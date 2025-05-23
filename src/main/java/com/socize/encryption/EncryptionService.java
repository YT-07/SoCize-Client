package com.socize.encryption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Stack;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.config.EncryptionConfig;
import com.socize.exception.FileTooSmallException;
import com.socize.utilities.FileIO;
import com.socize.utilities.FileSizeTracker;

/**
 * Provides encryption service.
 */
public class EncryptionService {
    private static final String ENCRYPTED_FILENAME_PREFIX = "encrypted_";
    private static final String ENCRYPTION_KEY_FILENAME_SUFFIX = ".key";
    private static final int MIN_FILE_TO_ENCRYPT_SIZE = 1;
    private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    private KeyGenerator keyGenerator;
    private SecureRandom secureRandom;
    private Cipher cipher;
    private ByteBuffer inputBuffer;
    private ByteBuffer outputBuffer;

    // Encryption process involves creating new files etc, and thus is intended to be atomic (whole process happens or doesn't happen at all)
    // These stacks are used for rolling back the encryption process in case something went wrong in the middle of it
    private Stack<Runnable> encryptionRollbackTask;

    /**
     * Creates a new encryption service with default settings.
     * 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public EncryptionService() throws NoSuchAlgorithmException, NoSuchPaddingException {
        keyGenerator = KeyGenerator.getInstance(EncryptionConfig.ALGORITHM);
        keyGenerator.init(EncryptionConfig.KEY_SIZE);

        secureRandom = SecureRandom.getInstanceStrong();

        cipher = Cipher.getInstance(EncryptionConfig.TRANSFORMATION);

        inputBuffer = ByteBuffer.allocate(EncryptionConfig.AES_BLOCK_SIZE);
        outputBuffer = ByteBuffer.allocate(EncryptionConfig.REQUIRED_OUTPUT_BUFFER_SIZE);

        encryptionRollbackTask = new Stack<>();
    }

    /**
     * Encrypts a file specified at {@code fileToEncrypt} and writes the new 
     * encrypted file and its encryption key to folder at {@code folderToSave}. 
     * 
     * This method is {@code synchronized} because it is stateful, the encryption 
     * service object will track the state of this encryption process for the purpose 
     * of detecting error and rolling back the encryption process or such.
     * 
     * Note: DO NOT log exceptions thrown by this method, logging is handled internally.
     * 
     * @param fileToEncrypt the file to encrypt
     * @param folderToSave the folder to save the encrypted file and its encryption key
     * @throws Exception if any error occur during the encryption process, provides user friendly messages by 
     * calling {@code getMessage()} for this exception object, message can be displayed to user directly
     */
    public synchronized void encryptFile(File fileToEncrypt, File folderToSave) throws Exception {

        try {

            String folderPath = folderToSave.getAbsolutePath();
            String filename = fileToEncrypt.getName();

            logger.info("Encryption process started, encrypting file '{}' and save to '{}' folder.", fileToEncrypt.getAbsolutePath(), folderPath);

            // Preliminary checks first, but DOES NOT guarantee that file path is still valid during actual file operation later on
            // e.g. user may delete file/folder after check but before actual file operation
            if(!fileToEncrypt.isFile()) {
                throw new FileNotFoundException("File '" + fileToEncrypt.getAbsolutePath() + "' is not a valid file, please select a valid file.");
            }

            if(!folderToSave.isDirectory()) {
                throw new IllegalArgumentException("File path '" + folderPath + "' is not a valid folder, please select a valid folder.");
            }

            if(fileToEncrypt.length() < MIN_FILE_TO_ENCRYPT_SIZE) {
                throw new FileTooSmallException("File size is too small, file must be at least " + MIN_FILE_TO_ENCRYPT_SIZE + " byte in size.");
            }

            // Encrypted file name and encryption key file name will have a prefix/suffix on their filename based on business logic
            String outputEncryptedFileName = ENCRYPTED_FILENAME_PREFIX + filename;
            logger.info("Output encrypted file's name determined to be '{}'", outputEncryptedFileName);

            String outputEncryptionKeyFileName = filename + ENCRYPTION_KEY_FILENAME_SUFFIX;
            logger.info("Output encryption key file's name determined to be '{}'", outputEncryptionKeyFileName);

            // Use Paths.get() to ensure compatibility between different OS
            // e.g. unix systems like linux use "/" as file path separator while Windows/MacOS use "\" or such
            Path outputEncryptedFilePath = Paths.get(folderPath, outputEncryptedFileName);
            logger.info("Full encrypted file's path determined to be '{}'", outputEncryptedFilePath.toString());

            Path outputEncryptionKeyFilePath = Paths.get(folderPath, outputEncryptionKeyFileName);
            logger.info("Full encryption key file's path determined to be '{}'", outputEncryptionKeyFilePath.toString());

            SecretKey secretKey = getSecretKey();

            saveSecretKey(secretKey, outputEncryptionKeyFilePath, encryptionRollbackTask);
            
            FileIO.createFileAtomic(outputEncryptedFilePath, encryptionRollbackTask);

            IvParameterSpec ivParameterSpec = getIvSpec();

            Files.write(outputEncryptedFilePath, ivParameterSpec.getIV(), StandardOpenOption.WRITE);
            logger.info("Successfully written the initialization vector to file '{}'.", outputEncryptedFilePath.toString());

            encrypt(fileToEncrypt.toPath(), outputEncryptedFilePath, secretKey, ivParameterSpec);

            // IMPORTANT NOTE
            // The reason #completeEncryption() is placed here and every catch block instead of using a finally block
            // is that in the case of a critical error such as StackOverflowError or OutOfMemoryError happening,
            // this function will not be handling it as it may lead to undefined behaviors and is meant for the JVM to handle it,
            // and thus we'll use a shutdown hook to gracefully terminate instead
            // but if we put #completeEncryption() in a finally block, it may clear out the rollback tasks stored in the stack,
            // which will prevent the JVM from executing the rollback procedure, so we'll place #completeEncryption()
            // at where the encryption process is considered complete instead.
            completeEncryption();

            // Exceptions need to have user friendly messages as they'll be displayed directly to the user
        } catch (FileNotFoundException fnfe) {
            logger.error(fnfe.getMessage(), fnfe);

            rollbackEncryption();
            throw new Exception(fnfe.getMessage());

        } catch(InvalidPathException ipe) {
            logger.error("Failed to convert '{}' to a valid file path. Reason: {}.", ipe.getInput(), ipe.getReason(), ipe);

            rollbackEncryption();
            throw new Exception("Failed to convert '" + ipe.getInput() + "' to a valid file path. Reason: " + ipe.getReason());

        } catch (IllegalArgumentException iae) {
            logger.error(iae.getMessage(), iae);

            rollbackEncryption();
            throw new Exception(iae.getMessage());

        } catch (FileAlreadyExistsException faee) {
            logger.error("File '{}' already exist, unable to create new file.", faee.getFile(), faee);

            rollbackEncryption();
            throw new Exception("File '" + faee.getFile() + "' already exist, unable to create new file, please remove or rename the existing file.");

        } catch (IOException ioe) {
            logger.error("An I/O exception had occured.", ioe);

            rollbackEncryption();
            throw new Exception("Something went wrong during file operations, check error log to see what happened.");

        } catch (FileTooSmallException ftse) {
            logger.error(ftse.getMessage(), ftse);

            rollbackEncryption();
            throw new Exception(ftse.getMessage());

        } catch (Exception e) {
            logger.error("An unhandled exception had occured.", e);

            rollbackEncryption();
            throw new Exception("Unknown error occured, check error log to see what happened.");
        }
    }

    /**
     * Gets a new secret key used for encryption based on algorithm used by key generator.
     * 
     * @return a new secret key
     */
    private SecretKey getSecretKey() {
        SecretKey key = keyGenerator.generateKey();
        logger.info("Successfully generated encryption key.");

        return key;
    }

    /**
     * Creates a new file at {@code filePath} that stores the encryption {@code key} 
     * and registers a function to rollback this operation at {@code rollbackStack}
     * 
     * @param key the encryption key to save
     * @param filePath the file path to save the encryption key
     * @param rollbackStack the stack to register a rollback function for this operation, or {@code null} if registration of rollback function is not required
     * @throws IOException if any error occur during file operation
     */
    private void saveSecretKey(SecretKey key, Path filePath, Stack<Runnable> rollbackStack) throws IOException {
        Runnable rollbackSaveSecretKeyTask = new Runnable() {

            @Override
            public void run() {

                try {

                    Files.deleteIfExists(filePath);
                    logger.info("File '{}' successfully deleted.", filePath.toString());

                } catch (Exception e) {
                    logger.error("Failed to delete encryption key file at '{}'.", filePath.toString(), e);
                }
            }
            
        };

        boolean keyFileCreated = false;
        
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            logger.info("Encryption key file successfully created at '{}'", filePath.toString());

            keyFileCreated = true;
            byte[] keyBytes = key.getEncoded();
            ByteBuffer buffer = ByteBuffer.wrap(keyBytes);

            fileChannel.write(buffer);
            logger.info("Encryption key successfully written to file.");

        } catch (FileAlreadyExistsException faee) {
            // DO NOT delete file if file already exist, could be another key file or such, deleting file may cause user to lose important data
            keyFileCreated = false;
            throw faee;

        } catch (IOException ioe) {
            keyFileCreated = true; // Just in case in some edge cases where the file is created but throws an IOException before registering that the file is created
            throw ioe;

        } finally {

            if(keyFileCreated && rollbackStack != null) {
                rollbackStack.push(rollbackSaveSecretKeyTask);
                logger.info("Successfully registered rollback task to delete the encryption key file at '{}'.", filePath.toString());
            }

        }
    }

    /**
     * Gets an initialization vector with cryptographically secure randomness.
     * 
     * @return a new initialization vector
     */
    private IvParameterSpec getIvSpec() {
        byte[] iv = new byte[EncryptionConfig.IV_SIZE];
        secureRandom.nextBytes(iv);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        logger.info("Successfully generated the initialization vector.");

        return ivParameterSpec;
    }

    /**
     * Reads the file data from {@code fileToEncrypt}, encrypts the data, then writes the encrypted data 
     * to {@code fileToWrite}. The file data is loaded chunk by chunk instead of the entire file into memory 
     * at once to allow for encryption of large files and to not consume too much memory.
     * 
     * @param fileToEncrypt the file path of the file to encrypt
     * @param fileToWrite the file path of the file to output the encrypted data
     * @param secretKey the encryption key
     * @param iv the initialization vector
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IOException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws FileTooSmallException
     */
    private void encrypt(Path fileToEncrypt, Path fileToWrite, SecretKey secretKey, IvParameterSpec iv) 
    throws 
    InvalidKeyException, 
    InvalidAlgorithmParameterException, 
    IOException,
    ShortBufferException, 
    IllegalBlockSizeException, 
    BadPaddingException,
    FileTooSmallException 
    {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        logger.info("Cipher successfully initialized to encrypt mode with encryption key and initialization vector.");

        // Just in case
        resetBuffers();
        
        try
        (
            FileChannel inputFile = FileChannel.open(fileToEncrypt, StandardOpenOption.READ);
            FileChannel outputFile = FileChannel.open(fileToWrite, StandardOpenOption.APPEND)
        ) 
        {
            logger.info("File channel for file to encrypt and file to write successfully opened.");

            // To make sure that during the actual file operation, the min file size 
            // rule is still adhered to, even with preliminary checks earlier 
            // e.g. file size may change after preliminary check but before actual file operation
            FileSizeTracker fileSizeTracker = new FileSizeTracker(MIN_FILE_TO_ENCRYPT_SIZE);
            logger.info("File size tracker created to ensure file size for file to encrypt is at least {} bytes.", MIN_FILE_TO_ENCRYPT_SIZE);

            logger.info("File encryption started.");

            while(true) {

                int bytesRead = inputFile.read(inputBuffer);

                inputBuffer.flip();
                fileSizeTracker.increment(bytesRead);

                if(bytesRead < 1) {
                    
                    if(fileSizeTracker.hasReachedMinFileSize()) {
                        logger.info("File size for file to encrypt had successfully reached the minimum file size required.");
                        cipher.doFinal(inputBuffer, outputBuffer);

                    } else {
                        throw new FileTooSmallException("File size is too small, file must be at least " + MIN_FILE_TO_ENCRYPT_SIZE + " byte in size.");

                    }

                } else {
                    cipher.update(inputBuffer, outputBuffer);

                }

                outputBuffer.flip();
                outputFile.write(outputBuffer);

                resetBuffers();

                if(bytesRead < 1) {
                    break;
                }
            }

            logger.info("File encryption completed without error.");
        }
    }

    /**
     * Perform all registered rollback functions to rollback the encryption process and completes it.
     * 
     * @see com.socize.encryption.EncryptionService#completeEncryption() completeEncryption()
     */
    private void rollbackEncryption() {
        logger.info("Rolling back this encryption process.");

        while(!encryptionRollbackTask.isEmpty()) {
            encryptionRollbackTask.pop().run();
        }

        logger.info("Encryption process successfully rollback.");
        completeEncryption();
    }

    /**
     * Perform all clean up task to complete an encryption process.
     */
    private void completeEncryption() {
        logger.info("Completing this encryption process.");

        encryptionRollbackTask.clear();
        resetBuffers();

        logger.info("Encryption process completed successfully.");
    }

    /**
     * Reset buffers to prepare for further encryption operations.
     */
    private void resetBuffers() {
        inputBuffer.clear();
        outputBuffer.clear();
    }

}