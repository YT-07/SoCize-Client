package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultDecryptionControllerFactory;

public class DefaultDecryptionControllerFactoryTest {
    
    @Test
    void decryptionControllerFactoryShouldNotReturnNull() {
        DefaultDecryptionControllerFactory controllerFactory = new DefaultDecryptionControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
