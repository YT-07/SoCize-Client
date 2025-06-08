package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.encryption.EncryptionController;

public class DefaultEncryptionControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        return new EncryptionController();
    }
    
}
