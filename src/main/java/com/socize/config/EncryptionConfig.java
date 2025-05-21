package com.socize.config;

/**
 * Configuration class that provide attributes that both the encryption and decryption process share.
 */
public class EncryptionConfig {    
    public static final String ALGORITHM = "AES";
    public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final int IV_SIZE = 16;
    public static final int KEY_SIZE = 256;
    public static final int AES_BLOCK_SIZE = 16;
    public static final int REQUIRED_OUTPUT_BUFFER_SIZE = AES_BLOCK_SIZE * 2; // Required by Cipher.update()

    private EncryptionConfig() {
        throw new UnsupportedOperationException("Config class not meant to be instantiated.");
    }
}
