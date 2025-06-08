package com.socize.encryption.spi;

import java.io.File;

public interface EncryptionService {

    void encryptFile(File fileToEncrypt, File folderToSave) throws Exception;
}