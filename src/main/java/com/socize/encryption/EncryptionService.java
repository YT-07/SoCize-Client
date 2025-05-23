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
     * @param fileToEncrypt the file to encrypt
     * @param folderToSave the folder to save the encrypted file and its encryption key
     * @throws Exception if any error occur during the encryption process, provides user friendly messages by 
     * calling {@code getMessage()} for this exception object, message can be displayed to user directly
     */
    public synchronized void encryptFile(File fileToEncrypt, File folderToSave) throws Exception {
        
        try {

            String folderPath = folderToSave.getAbsolutePath();
            String filename = fileToEncrypt.getName();

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
            String outputEncryptionKeyFileName = filename + ENCRYPTION_KEY_FILENAME_SUFFIX;

            // Use Paths.get() to ensure compatibility between different OS
            // e.g. unix systems like linux use "/" as file path separator while Windows/MacOS use "\" or such
            Path outputEncryptedFilePath = Paths.get(folderPath, outputEncryptedFileName);
            Path outputEncryptionKeyFilePath = Paths.get(folderPath, outputEncryptionKeyFileName);

            SecretKey secretKey = getSecretKey();
            saveSecretKey(secretKey, outputEncryptionKeyFilePath, encryptionRollbackTask);
            
            FileIO.createFileAtomic(outputEncryptedFilePath, encryptionRollbackTask);

            IvParameterSpec ivParameterSpec = getIvSpec();
            Files.write(outputEncryptedFilePath, ivParameterSpec.getIV(), StandardOpenOption.WRITE);

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
        } catch (FileNotFoundException fnfe) { // TODO: Log detailed error messages for debugging
            rollbackEncryption();
            completeEncryption();
            throw new Exception(fnfe.getMessage());

        } catch(InvalidPathException ipe) {
            rollbackEncryption();
            completeEncryption();
            throw new Exception("Failed to obtain file path '" + ipe.getInput() + "', " + ipe.getReason());

        } catch (IllegalArgumentException iae) {
            rollbackEncryption();
            completeEncryption();
            throw new Exception(iae.getMessage());

        } catch (FileAlreadyExistsException faee) {
            rollbackEncryption();
            completeEncryption();
            throw new Exception("File '" + faee.getFile() + "' already exist, unable to create new file, please remove or rename the existing file.");

        } catch (IOException ioe) {
            rollbackEncryption();
            completeEncryption();
            throw new Exception("Something went wrong during file operations, check error log to see what happened.");

        } catch (FileTooSmallException ftse) {
            rollbackEncryption();
            completeEncryption();
            throw new Exception(ftse.getMessage());

        } catch (Exception e) {
            rollbackEncryption();
            completeEncryption();
            throw new Exception("Unknown error occured, check error log to see what happened.");
        }
    }

    /**
     * Gets a new secret key used for encryption based on algorithm used by key generator.
     * 
     * @return a new secret key
     */
    private SecretKey getSecretKey() {
        return keyGenerator.generateKey();
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

                } catch (Exception e) {
                    // TODO: Log error if fail to delete key file
                }
            }
            
        };

        boolean keyFileCreated = false;
        
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            
            keyFileCreated = true;
            byte[] keyBytes = key.getEncoded();
            ByteBuffer buffer = ByteBuffer.wrap(keyBytes);

            fileChannel.write(buffer);

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

        return new IvParameterSpec(iv);
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

        // Just in case
        resetBuffers();
        
        try
        (
            FileChannel inputFile = FileChannel.open(fileToEncrypt, StandardOpenOption.READ);
            FileChannel outputFile = FileChannel.open(fileToWrite, StandardOpenOption.APPEND)
        ) 
        {

            // To make sure that during the actual file operation, the min file size 
            // rule is still adhered to, even with preliminary checks earlier 
            // e.g. file size may change after preliminary check but before actual file operation
            FileSizeTracker fileSizeTracker = new FileSizeTracker(MIN_FILE_TO_ENCRYPT_SIZE);

            while(true) {

                int bytesRead = inputFile.read(inputBuffer);

                inputBuffer.flip();
                fileSizeTracker.increment(bytesRead);

                if(bytesRead < 1) {
                    
                    if(fileSizeTracker.hasReachedMinFileSize()) {
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

        }
    }

    /**
     * Perform all registered rollback functions to rollback the encryption process.
     */
    private void rollbackEncryption() {

        while(!encryptionRollbackTask.isEmpty()) {
            encryptionRollbackTask.pop().run();
        }
    }

    /**
     * Perform all clean up task to complete an encryption process.
     */
    private void completeEncryption() {
        encryptionRollbackTask.clear();
        resetBuffers();
    }

    /**
     * Reset buffers to prepare for further encryption operations.
     */
    private void resetBuffers() {
        inputBuffer.clear();
        outputBuffer.clear();
    }

}