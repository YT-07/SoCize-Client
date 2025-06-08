package com.socize.encryption.spi;

import java.io.File;

public interface DecryptionService {

    void decryptFile(File fileToDecrypt, File encryptionKeyFile, File fileToSave) throws Exception;
}