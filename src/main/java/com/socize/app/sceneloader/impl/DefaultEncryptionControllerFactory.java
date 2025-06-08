package com.socize.app.sceneloader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.encryption.impl.DefaultEncryptionService;
import com.socize.encryption.spi.EncryptionService;
import com.socize.pages.encryption.EncryptionController;

public class DefaultEncryptionControllerFactory implements SceneControllerFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEncryptionControllerFactory.class);

    @Override
    public Object createDefault() {
        EncryptionService encryptionService = null;

        try {

            encryptionService = new DefaultEncryptionService();

        } catch (Exception e) {
            logger.error("Error initializing default encryption service.", e);
            throw new RuntimeException();
        }

        return new EncryptionController(encryptionService);
    }
    
}
