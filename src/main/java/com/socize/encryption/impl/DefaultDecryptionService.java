package com.socize.encryption.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Stack;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.config.EncryptionConfig;
import com.socize.encryption.spi.DecryptionService;
import com.socize.exception.FileTooSmallException;
import com.socize.exception.InvalidFileFormatException;
import com.socize.utilities.FileSizeTracker;

/**
 * Provides decryption service.
 */
public class DefaultDecryptionService implements DecryptionService {
    private Cipher cipher;

    private ByteBuffer inputBuffer;
    private ByteBuffer outputBuffer;
    private ByteBuffer ivBuffer;

    private Stack<Runnable> decryptionRollbackTask;

    private static final int MIN_FILE_TO_DECRYPT_SIZE = EncryptionConfig.IV_SIZE + EncryptionConfig.AES_BLOCK_SIZE;
    private static final Logger logger = LoggerFactory.getLogger(DefaultDecryptionService.class);

    public DefaultDecryptionService() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance(EncryptionConfig.TRANSFORMATION);

        inputBuffer = ByteBuffer.allocate(EncryptionConfig.AES_BLOCK_SIZE);
        outputBuffer = ByteBuffer.allocate(EncryptionConfig.REQUIRED_OUTPUT_BUFFER_SIZE);
        ivBuffer = ByteBuffer.allocate(EncryptionConfig.IV_SIZE);

        decryptionRollbackTask = new Stack<>();
    }

    /**
     * Decrypts a file specified at {@code fileToDecrypt} using the encryption key at {@code encryptionKeyFile}, 
     * then writes the decrypted data to {@code fileToSave}.
     * 
     * This method is {@code synchronized} because it is stateful, the decryption 
     * service object will track the state of this decryption process for the purpose 
     * of detecting error and rolling back the decryption process or such.
     * 
     * @param fileToDecrypt the file to decrypt
     * @param encryptionKeyFile the encryption key file
     * @param fileToSave the file to save the decrypted data to
     * @throws Exception if any error occur during the decryption process, provides user friendly messages by 
     * calling {@code getMessage()} for this exception object, message can be displayed to user directly
     */
    @Override
    public synchronized void decryptFile(File fileToDecrypt, File encryptionKeyFile, File fileToSave) throws Exception {

        try {

            logger.info
            (
                "Decryption process started, decrypting file '{}', with encryption key at '{}', and save to '{}'.",
                fileToDecrypt.getAbsolutePath(),
                encryptionKeyFile.getAbsolutePath(),
                fileToSave.getAbsolutePath()
            );

            // Preliminary checks, but DOES NOT guarantee that file path is still valid during actual file operation later on
            // e.g. user may delete file/folder after check but before actual file operation
            if(!encryptionKeyFile.isFile()) {
                throw new FileNotFoundException("File '" + encryptionKeyFile.getAbsolutePath() + "' is not a valid file, please select a valid file.");
            }

            int encryptionKeySizeInBytes = EncryptionConfig.KEY_SIZE / 8;

            if(encryptionKeyFile.length() != encryptionKeySizeInBytes) {
                throw new InvalidKeyException("Invalid encryption key, expected file size to be " + encryptionKeySizeInBytes + " bytes, please select a valid key file.");
            }

            if(!fileToDecrypt.isFile()) {
                throw new FileNotFoundException("File '" + fileToDecrypt.getAbsolutePath() + "' is not a valid file, please select a valid file.");
            }

            if(fileToDecrypt.length() < MIN_FILE_TO_DECRYPT_SIZE) {
                throw new FileTooSmallException("Invalid file size for file to decrypt, file is required to be " + MIN_FILE_TO_DECRYPT_SIZE + " bytes or more in size, please select a valid file.");
            }

            // AES operates on 16 bytes blocks, so file must also be a factor of 16 bytes
            // The actual validation and verification will be handled by the cipher during actual file decryption, this is just for early warning
            if(fileToDecrypt.length() % 16 != 0) {
                throw new IllegalArgumentException("Invalid file size for file to decrypt, file size must be a multiple of 16 bytes, please select a valid file.");
            }

            // User may provide existing or non-existing file through save dialog
            // Just have to make sure file is not a directory
            if(fileToSave.isDirectory()) {
                throw new FileNotFoundException("File '" + fileToSave.getAbsolutePath() + "' is not a valid file to save, please select a valid file to save.");
            }

            SecretKey key = readEncryptionKey(encryptionKeyFile, encryptionKeySizeInBytes);
            readIvAndDecrypt(fileToDecrypt, fileToSave, key, decryptionRollbackTask);

            completeDecryption();

            // Exceptions need to have user friendly messages as they'll be displayed directly to the user
        } catch (FileNotFoundException fnfe) {
            logger.error(fnfe.getMessage(), fnfe);

            rollbackDecryption();
            throw new Exception(fnfe.getMessage());

        } catch (InvalidKeyException ike) {
            logger.error(ike.getMessage(), ike);

            rollbackDecryption();
            throw new Exception(ike.getMessage());

        } catch (IllegalArgumentException iae) {
            logger.error(iae.getMessage(), iae);

            rollbackDecryption();
            throw new Exception(iae.getMessage());

        } catch (InvalidFileFormatException iffe) {
            logger.error(iffe.getMessage(), iffe);

            rollbackDecryption();
            throw new Exception(iffe.getMessage());

        } catch (IOException ioe) {
            logger.error("An I/O exception had occured.", ioe);

            rollbackDecryption();
            throw new Exception("Something went wrong during file operations, check error log to see what happened.");

        } catch (FileTooSmallException ftse) {
            logger.error(ftse.getMessage(), ftse);

            rollbackDecryption();
            throw new Exception(ftse.getMessage());

        } catch (Exception e) {
            logger.error("An unhandled exception had occured.", e);

            rollbackDecryption();
            throw new Exception("Unknown error occured, check error log to see what happened.");
        }
    }

    /**
     * Reads the file and uses the file data to construct a {@link javax.crypto.SecretKey SecretKey} object.
     * 
     * @param encryptionKeyFile the file that contains the encryption key
     * @param encryptionKeySize the expected encryption key size in bytes
     * @return the encryption key object
     * @throws IOException if any error happen during file operation
     * @throws InvalidKeyException if the size of the encryption key in the file does not match with {@code encryptionKeySize}
     */
    private SecretKey readEncryptionKey(File encryptionKeyFile, int encryptionKeySize) throws IOException, InvalidKeyException {
        ByteBuffer buffer = ByteBuffer.allocate(encryptionKeySize);

        try (FileChannel fileChannel = FileChannel.open(encryptionKeyFile.toPath(), StandardOpenOption.READ)){
            logger.info("Encryption key file successfully opened.");

            fileChannel.read(buffer);
            buffer.flip();

            if(buffer.remaining() != encryptionKeySize) {
                throw new InvalidKeyException("Invalid encryption key, expected file size to be " + encryptionKeySize + " bytes.");
            }

            SecretKey key = new SecretKeySpec(buffer.array(), EncryptionConfig.ALGORITHM);
            logger.info("Successfully parsed encryption key from encryption key file.");

            return key;
        }
    }

    /**
     * Reads the file data from {@code fileToDecrypt}, uses {@code key} as the encryption key and 
     * attempt to write the decrypted data to {@code fileToSave}. This will also register a rollback 
     * function in {@code rollbackStack} to call in case some error happened during the decryption process. 
     * The file data is loaded chunk by chunk instead of the entire file into memory at once to allow 
     * for decryption of large files and to not consume too much memory.
     * 
     * @param fileToDecrypt the file to decrypt
     * @param fileToSave the file to write the decrypted data to
     * @param key the encryption key
     * @param rollbackStack the stack to register a rollback function, or {@code null} if its not required
     * @throws IOException
     * @throws InvalidFileFormatException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws FileTooSmallException
     */
    private void readIvAndDecrypt(File fileToDecrypt, File fileToSave, SecretKey key, Stack<Runnable> rollbackStack) 
    throws 
    IOException, 
    InvalidFileFormatException, 
    InvalidKeyException, 
    InvalidAlgorithmParameterException, 
    ShortBufferException, 
    IllegalBlockSizeException, 
    BadPaddingException, 
    FileTooSmallException 
    {

        Runnable rollbackTask = new Runnable() {

            @Override
            public void run() {

                try {

                    Files.deleteIfExists(fileToSave.toPath());
                    logger.info("File '{}' successfully deleted.", fileToSave.getAbsolutePath());

                } catch (IOException e) {
                    logger.error("Failed to delete decrypted file at '{}'.", fileToSave.getAbsolutePath(), e);
                }
            }
            
        };

        try
        (
            FileChannel inputChannel = FileChannel.open(fileToDecrypt.toPath(), StandardOpenOption.READ);
            FileChannel outputChannel = FileChannel.open(fileToSave.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE)
        ) 
        {
            logger.info("File channel for file to decrypt and file to save successfully opened.");

            boolean isReadingIvSection = true; // First few bytes in file will be the initialization vector, so is reading iv first
            boolean cipherIsInitialized = false;

            resetBuffers(); // Just in case
            IvParameterSpec iv = null;

            FileSizeTracker fileSizeTracker = new FileSizeTracker(MIN_FILE_TO_DECRYPT_SIZE);
            logger.info("File size tracker created to ensure file size for file to decrypt is at least {} bytes.", MIN_FILE_TO_DECRYPT_SIZE);

            logger.info("File decryption started.");

            while(true) {

                if(isReadingIvSection) {
                    isReadingIvSection = false;

                    logger.info("Attempting to read initialization vector from file.");

                    int ivBytesRead = inputChannel.read(ivBuffer);
                    fileSizeTracker.increment(ivBytesRead);

                    // Assume that the iv buffer's' capacity is the same as the iv's size, e.g. 16 bytes ByteBuffer for 16 bytes iv size,
                    // and check if it fully filled the buffer or not, because constructing an iv object needs a byte array instead of a byte buffer, 
                    // so need to make sure the byte array is full and valid by checking if byte buffer is fully filled.
                    if(ivBytesRead != ivBuffer.capacity()) {
                        throw new InvalidFileFormatException("File format for file to decrypt is invalid, the initialization vector is missing or corrupted.");
                    }

                    iv = new IvParameterSpec(ivBuffer.array());
                    logger.info("Initialization vector successfully read and parsed from file.");

                    continue;
                }

                if(!cipherIsInitialized) {
                    cipher.init(Cipher.DECRYPT_MODE, key, iv);

                    cipherIsInitialized = true;
                    logger.info("Cipher successfully initialized to decrypt mode with encryption key and initialization vector.");

                    continue;
                }

                int bytesRead = inputChannel.read(inputBuffer);

                inputBuffer.flip();
                fileSizeTracker.increment(bytesRead);

                if(bytesRead < 1) {

                    if(fileSizeTracker.hasReachedMinFileSize()) {
                        logger.info("File size for file to decrypt had successfully reached the minimum file size required.");
                        cipher.doFinal(inputBuffer, outputBuffer);

                    } else {
                        throw new FileTooSmallException("File size is too small, file must be at least " + MIN_FILE_TO_DECRYPT_SIZE + " byte in size.");

                    }

                } else {
                    cipher.update(inputBuffer, outputBuffer);

                }

                outputBuffer.flip();
                outputChannel.write(outputBuffer);

                resetBuffers();

                if(bytesRead < 1) {
                    break;
                }
            }

            logger.info("File decryption completed without error.");

        } finally {

            // Register the rollback task regardless, if anything goes wrong during decryption, 
            // file to save should not exist.
            if(rollbackStack != null) {
                rollbackStack.push(rollbackTask);
                logger.info("Successfully registered rollback task to delete the decrypted file at '{}'.", fileToSave.getAbsolutePath());
            }

        }
    }

    /**
     * Perform all registered rollback functions to rollback the decryption process and completes it.
     * 
     * @see com.socize.encryption.impl.DefaultDecryptionService#completeDecryption() completeDecryption()
     */
    private void rollbackDecryption() {
        logger.info("Rolling back this decryption process.");

        while(!decryptionRollbackTask.isEmpty()) {
            decryptionRollbackTask.pop().run();
        }

        logger.info("Decryption process successfully rollback.");
        completeDecryption();
    }

    /**
     * Perform all clean up task to complete a decryption process.
     */
    private void completeDecryption() {
        logger.info("Completing this decryption process.");

        decryptionRollbackTask.clear();
        resetBuffers();

        logger.info("Decryption process completed successfully.");
    }

    /**
     * Reset buffers to prepare for further decryption operations.
     */
    private void resetBuffers() {
        inputBuffer.clear();
        outputBuffer.clear();
        ivBuffer.clear();
    }
}
