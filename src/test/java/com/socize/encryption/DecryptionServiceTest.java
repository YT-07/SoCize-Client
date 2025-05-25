package com.socize.encryption;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.socize.config.EncryptionConfig;

class DecryptionServiceTest {
    private EncryptionService encryptionService;
    private DecryptionService decryptionService;
    private static final String dataToEncrypt = "Hello World";
    private static final String testFileName = "test.txt";

    @TempDir
    private Path tempDir;

    private File fileToEncrypt;
    private File folderToSave;

    private File fileToDecrypt;
    private File encryptionKeyFile;
    private File fileToSave;

    @BeforeEach
    void setup() throws Exception {
        encryptionService = new EncryptionService();
        decryptionService = new DecryptionService();

        fileToEncrypt = tempDir.resolve(testFileName).toFile();
        Files.write(fileToEncrypt.toPath(), dataToEncrypt.getBytes());

        folderToSave = tempDir.toFile();

        fileToDecrypt = new File(folderToSave, "encrypted_" + testFileName);
        encryptionKeyFile = new File(folderToSave, testFileName + ".key");
        fileToSave = tempDir.resolve("decrypted-file.txt").toFile();

        encryptionService.encryptFile(fileToEncrypt, folderToSave);
    }

    @Test
    void shouldThrowException_IfEncryptionKeyFileDoesNotExists() {
        File nonExistentKeyFile = new File("non-existent-keyfile.key");

        assertThrows(Exception.class, () -> {
            decryptionService.decryptFile(fileToDecrypt, nonExistentKeyFile, fileToSave);
        });
    }

    @Test
    void shouldThrowException_IfEncryptionKeyFileSizeIsInvalid() throws IOException {
        int expectedKeySizeInBytes = EncryptionConfig.KEY_SIZE / 8;
        int invalidKeySize = expectedKeySizeInBytes + 1;

        byte[] dataToWrite = new byte[invalidKeySize];

        Random random = new Random();
        random.nextBytes(dataToWrite);

        Path testKeyFile = tempDir.resolve("temp-key-file.key");
        Files.write(testKeyFile, dataToWrite);

        assertThrows(Exception.class, () -> {
            decryptionService.decryptFile(fileToDecrypt, testKeyFile.toFile(), fileToSave);
        });
    }

    @Test
    void shouldThrowException_IfFileToDecryptDoesNotExists() {
        File nonExistentFileToDecrypt = new File("non-existent-file.txt");

        assertThrows(Exception.class, () -> {
            decryptionService.decryptFile(nonExistentFileToDecrypt, encryptionKeyFile, fileToSave);
        });
    }

    @Test
    void shouldThrowException_IfFileToDecryptFileSizeDoesNotMeetMinRequiredFileSize() throws IOException {
        int minRequiredFileSize = EncryptionConfig.AES_BLOCK_SIZE + EncryptionConfig.IV_SIZE;
        int invalidFileSize = minRequiredFileSize - 1;

        byte[] dataToWrite = new byte[invalidFileSize];

        Random random = new Random();
        random.nextBytes(dataToWrite);

        Path testFileToDecrypt = tempDir.resolve("temp-file-to-decrypt.txt");
        Files.write(testFileToDecrypt, dataToWrite);

        assertThrows(Exception.class, () -> {
            decryptionService.decryptFile(testFileToDecrypt.toFile(), encryptionKeyFile, fileToSave);
        });
    }

    @Test
    void shouldThrowException_IfFileToDecryptFileSizeIsNotValidCipherBlockSize() throws IOException {
        int validFileSize = EncryptionConfig.IV_SIZE + (EncryptionConfig.AES_BLOCK_SIZE * 5);
        int invalidFileSize = validFileSize + 1;

        byte[] dataToWrite = new byte[invalidFileSize];

        Random random = new Random();
        random.nextBytes(dataToWrite);

        Path testFileToDecrypt = tempDir.resolve("temp-file-to-decrypt.txt");
        Files.write(testFileToDecrypt, dataToWrite);

        assertThrows(Exception.class, () -> {
            decryptionService.decryptFile(testFileToDecrypt.toFile(), encryptionKeyFile, fileToSave);
        });
    }

    @Test
    void shouldThrowException_IfFileToSaveIsNotValidFile() {
        File invalidFile = tempDir.toFile();

        assertThrows(Exception.class, () -> {
            decryptionService.decryptFile(fileToDecrypt, encryptionKeyFile, invalidFile);
        });
    }

    @Test
    void decryptedDataShouldBeSameWithOriginalDataBeforeEncryption() throws Exception {
        decryptionService.decryptFile(fileToDecrypt, encryptionKeyFile, fileToSave);

        byte[] originalData = dataToEncrypt.getBytes();
        byte[] decryptedData = Files.readAllBytes(fileToSave.toPath());

        assertTrue(Arrays.equals(originalData, decryptedData));
    }
}
