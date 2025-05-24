package com.socize.encryption;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.socize.config.EncryptionConfig;

class EncryptionServiceTest {
    
    EncryptionService encryptionService;

    @TempDir
    Path tempDir;

    File testFile;
    File saveFolder;

    @BeforeEach
    void setup() throws NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        encryptionService = new EncryptionService();

        testFile = tempDir.resolve("test.txt").toFile();
        Files.write(testFile.toPath(), "Hello World".getBytes());

        saveFolder = tempDir.toFile();
    }

    @Test
    void shouldThrowException_IfAnyParameterIsNull() {

        assertAll(
            () -> {
                assertThrows(Exception.class, () -> {encryptionService.encryptFile(null, saveFolder);});
            },

            () -> {
                assertThrows(Exception.class, () -> {encryptionService.encryptFile(testFile, null);});
            }
        );
    }

    @Test
    void shouldThrowException_IfFileToEncryptDoesNotExists() {
        File nonExistentFile = new File("non-existent-file.txt");

        assertThrows(Exception.class, () -> {
            encryptionService.encryptFile(nonExistentFile, saveFolder);
        });
    }

    @Test
    void shouldThrowException_IfFolderToSaveIsNotDirectory() {
        File fileInsteadOfFolder = tempDir.resolve("file-instead-of-folder.txt").toFile();

        assertThrows(Exception.class, () -> {
            encryptionService.encryptFile(testFile, fileInsteadOfFolder);
        });
    }

    @Test
    void shouldThrowException_IfFileToEncryptSizeIsTooSmall() {
        File emptyFile = tempDir.resolve("empty-file.txt").toFile();

        assertThrows(Exception.class, () -> {
            encryptionService.encryptFile(emptyFile, saveFolder);
        });
    }

    @Test
    void shouldCreateEncryptedFileAndKey() throws Exception {
        encryptionService.encryptFile(testFile, saveFolder);

        File encryptedFile = new File(saveFolder, "encrypted_" + testFile.getName());
        assertTrue(encryptedFile.exists());

        File encryptionKeyFile = new File(saveFolder, testFile.getName() + ".key");
        assertTrue(encryptionKeyFile.exists());

        int minEncryptedFileSize = EncryptionConfig.IV_SIZE + EncryptionConfig.AES_BLOCK_SIZE;
        assertTrue(encryptedFile.length() >= minEncryptedFileSize);

        int encryptionKeySizeInBytes = EncryptionConfig.KEY_SIZE / 8;
        assertTrue(encryptionKeyFile.length() == encryptionKeySizeInBytes);
    }
}
