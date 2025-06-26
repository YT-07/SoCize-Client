package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultEncryptionControllerFactory;

public class DefaultEncryptionControllerFactoryTest {
    
    @Test
    void encryptionControllerFactoryShouldNotReturnNull() {
        DefaultEncryptionControllerFactory controllerFactory = new DefaultEncryptionControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
