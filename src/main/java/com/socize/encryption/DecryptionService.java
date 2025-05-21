package com.socize.encryption;

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

import com.socize.config.EncryptionConfig;
import com.socize.exception.FileTooSmallException;
import com.socize.exception.InvalidFileFormatException;
import com.socize.utilities.FileSizeTracker;

public class DecryptionService {
    private Cipher cipher;

    private ByteBuffer inputBuffer;
    private ByteBuffer outputBuffer;
    private ByteBuffer ivBuffer;

    private Stack<Runnable> decryptionRollbackTask;

    private static final int MIN_FILE_TO_DECRYPT_SIZE = EncryptionConfig.IV_SIZE + EncryptionConfig.AES_BLOCK_SIZE;

    public DecryptionService() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance(EncryptionConfig.TRANSFORMATION);

        inputBuffer = ByteBuffer.allocate(EncryptionConfig.AES_BLOCK_SIZE);
        outputBuffer = ByteBuffer.allocate(EncryptionConfig.REQUIRED_OUTPUT_BUFFER_SIZE);
        ivBuffer = ByteBuffer.allocate(EncryptionConfig.IV_SIZE);

        decryptionRollbackTask = new Stack<>();
    }

    public synchronized void decryptFile(File fileToDecrypt, File encryptionKeyFile, File fileToSave) throws Exception {

        try {
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
            if(fileToDecrypt.length() % 16 != 0) {
                throw new IllegalArgumentException("Invalid file size for file to decrypt, file size must be a multiple of 16 bytes, please select a valid file.");
            }

            if(!fileToSave.isFile()) {
                throw new FileNotFoundException("File '" + fileToSave.getAbsolutePath() + "' is not a valid file to save, please select a valid file to save.");
            }

            SecretKey key = readEncryptionKey(encryptionKeyFile, encryptionKeySizeInBytes);
            readIvAndDecrypt(fileToDecrypt, fileToSave, key, decryptionRollbackTask);

            // IMPORTANT NOTE
            // The reason #completeEncryption() is placed here and every catch block instead of using a finally block
            // is that in the case of a critical error such as StackOverflowError or OutOfMemoryError happening,
            // this function will not be handling it as it may lead to undefined behaviors and is meant for the JVM to handle it,
            // and thus we'll use a shutdown hook to gracefully terminate instead
            // but if we put #completeEncryption() in a finally block, it may clear out the rollback tasks stored in the stack,
            // which will prevent the JVM from executing the rollback procedure, so we'll place #completeEncryption()
            // at where the encryption process is considered complete instead.
            completeDecryption();

            // Exceptions need to have user friendly messages as they'll be displayed directly to the user
        } catch (FileNotFoundException fnfe) { // TODO: Log detailed error messages for debugging
            rollbackDecryption();
            completeDecryption();
            throw new Exception(fnfe.getMessage());

        } catch (InvalidKeyException ike) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception(ike.getMessage());

        } catch (InvalidFileFormatException iffe) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception(iffe.getMessage());

        } catch (IOException ioe) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception("Something went wrong during file operations, check error log to see what happened.");

        } catch (FileTooSmallException ftse) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception(ftse.getMessage());

        } catch (Exception e) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception("Unknown error occured, check error log to see what happened.");
        }
    }

    private SecretKey readEncryptionKey(File encryptionKeyFile, int encryptionKeySize) throws IOException, InvalidKeyException {
        ByteBuffer buffer = ByteBuffer.allocate(encryptionKeySize);

        try (FileChannel fileChannel = FileChannel.open(encryptionKeyFile.toPath(), StandardOpenOption.READ)){
            fileChannel.read(buffer);
            buffer.flip();

            if(buffer.remaining() != encryptionKeySize) {
                throw new InvalidKeyException("Invalid encryption key, expected file size to be " + encryptionKeySize + " bytes.");
            }

            return new SecretKeySpec(buffer.array(), EncryptionConfig.ALGORITHM);
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

                } catch (IOException e) {
                    // TODO: Log error if fail to delete file
                }
            }
            
        };

        try
        (
            FileChannel inputChannel = FileChannel.open(fileToDecrypt.toPath(), StandardOpenOption.READ);
            FileChannel outputChannel = FileChannel.open(fileToSave.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE)
        ) 
        {
            boolean isReadingIvSection = true; // First few bytes in file will be the initialization vector, so is reading iv first
            boolean cipherIsInitialized = false;

            resetBuffers(); // Just in case
            IvParameterSpec iv = null;
            FileSizeTracker fileSizeTracker = new FileSizeTracker(MIN_FILE_TO_DECRYPT_SIZE);

            while(true) {

                if(isReadingIvSection) {
                    isReadingIvSection = false;

                    int ivBytesRead = inputChannel.read(ivBuffer);

                    // Assume that the iv buffer's' capacity is the same as the iv's size, e.g. 16 bytes ByteBuffer for 16 bytes iv size,
                    // and check if it fully filled the buffer or not, because constructing an iv object needs a byte array instead of a byte buffer, 
                    // so need to make sure the byte array is full and valid by checking if byte buffer is fully filled.
                    if(ivBytesRead != ivBuffer.capacity()) {
                        throw new InvalidFileFormatException("File format for file to decrypt is invalid, the initialization vector is missing or corrupted.");
                    }

                    iv = new IvParameterSpec(ivBuffer.array());
                    continue;
                }

                if(!cipherIsInitialized) {
                    cipher.init(Cipher.DECRYPT_MODE, key, iv);

                    cipherIsInitialized = true;
                    continue;
                }

                int bytesRead = inputChannel.read(inputBuffer);

                inputBuffer.flip();
                fileSizeTracker.increment(bytesRead);

                if(bytesRead < 1) {

                    if(fileSizeTracker.hasReachedMinFileSize()) {
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

        } finally {

            // Register the rollback task regardless, if anything goes wrong during decryption, 
            // file to save should not exist.
            if(rollbackStack != null) {
                rollbackStack.push(rollbackTask);
            }

        }
    }

    /**
     * Perform all registered rollback functions to rollback the decryption process.
     */
    private void rollbackDecryption() {

        while(!decryptionRollbackTask.isEmpty()) {
            decryptionRollbackTask.pop().run();
        }
    }

    /**
     * Perform all clean up task to complete a decryption process.
     */
    private void completeDecryption() {
        decryptionRollbackTask.clear();
        resetBuffers();
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
