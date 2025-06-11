package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.encryption.impl.DefaultDecryptionService;
import com.socize.encryption.spi.DecryptionService;
import com.socize.pages.PageController;
import com.socize.pages.decryption.DecryptionController;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultDecryptionControllerFactory implements SceneControllerFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDecryptionControllerFactory.class);

    @Override
    public PageController createDefault() {
        TextStyler textStyler = DefaultTextStyler.getInstance();
        DecryptionService decryptionService = null;

        try {

            decryptionService = new DefaultDecryptionService();

        } catch (Exception e) {
            logger.error("Error initializing default decryption service.", e);
            throw new RuntimeException();
        }

        return new DecryptionController(textStyler, decryptionService);
    }
    
}
