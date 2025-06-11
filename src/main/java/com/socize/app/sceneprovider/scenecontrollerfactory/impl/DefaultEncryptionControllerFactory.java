package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.encryption.impl.DefaultEncryptionService;
import com.socize.encryption.spi.EncryptionService;
import com.socize.pages.TransitionablePage;
import com.socize.pages.encryption.EncryptionController;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultEncryptionControllerFactory implements SceneControllerFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEncryptionControllerFactory.class);

    @Override
    public TransitionablePage createDefault() {
        EncryptionService encryptionService = null;

        try {

            encryptionService = new DefaultEncryptionService();

        } catch (Exception e) {
            logger.error("Error initializing default encryption service.", e);
            throw new RuntimeException();
        }

        TextStyler textStyler = DefaultTextStyler.getInstance();

        return new EncryptionController(encryptionService, textStyler);
    }
    
}
