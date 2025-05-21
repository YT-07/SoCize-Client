package com.socize.encryption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.util.Stack;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.socize.config.EncryptionConfig;
import com.socize.exception.FileTooSmallException;

public class DecryptionService {
    private Stack<Runnable> decryptionRollbackTask;
    private ByteBuffer inputBuffer;
    private ByteBuffer outputBuffer;

    public DecryptionService() {
        decryptionRollbackTask = new Stack<>();

        inputBuffer = ByteBuffer.allocate(EncryptionConfig.AES_BLOCK_SIZE);
        outputBuffer = ByteBuffer.allocate(EncryptionConfig.REQUIRED_OUTPUT_BUFFER_SIZE);
    }

    public synchronized void decryptFile(File fileToDecrypt, File encryptionKeyFile, File fileToSave) throws Exception {

        try {
            // Preliminary checks, but DOES NOT guarantee that file path is still valid during actual file operation later on
            // e.g. user may delete file/folder after check but before actual file operation
            if(!encryptionKeyFile.isFile()) {
                throw new FileNotFoundException("File '" + encryptionKeyFile.getAbsolutePath() + "' is not a valid file.");
            }

            int encryptionKeySizeInBytes = EncryptionConfig.KEY_SIZE / 8;

            if(encryptionKeyFile.length() != encryptionKeySizeInBytes) {
                throw new InvalidKeyException("Invalid encryption key, expected file size to be " + encryptionKeySizeInBytes + " bytes.");
            }

            if(!fileToDecrypt.isFile()) {
                throw new FileNotFoundException("File '" + fileToDecrypt.getAbsolutePath() + "' is not a valid file.");
            }

            int minFileToDecryptSize = EncryptionConfig.IV_SIZE + EncryptionConfig.AES_BLOCK_SIZE;

            if(fileToDecrypt.length() < minFileToDecryptSize) {
                throw new FileTooSmallException("Invalid file size for file to decrypt, file is required to be " + minFileToDecryptSize + " bytes or more in size.");
            }

            // AES operates on 16 bytes blocks, so file must also be a factor of 16 bytes
            if(fileToDecrypt.length() % 16 != 0) {
                throw new IllegalArgumentException("Invalid file size for file to decrypt, it must be a multiple of 16 bytes.");
            }

            if(!fileToSave.getParentFile().isDirectory()) {
                throw new IllegalArgumentException("File path '" + fileToSave.getParent() + "' is not a valid folder to save.");
            }

            SecretKey key = readEncryptionKey(encryptionKeyFile, encryptionKeySizeInBytes);

        } catch (FileNotFoundException fnfe) { // TODO: Log detailed error messages for debugging
            rollbackDecryption();
            completeDecryption();
            throw new Exception(fnfe.getMessage());

        } catch (InvalidKeyException ike) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception(ike.getMessage());

        } catch (IOException ioe) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception("Something went wrong during file operation.");

        } catch (FileTooSmallException ftse) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception(ftse.getMessage());

        } catch (Exception e) {
            rollbackDecryption();
            completeDecryption();
            throw new Exception("Unknown error occured...");
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
    }
}
