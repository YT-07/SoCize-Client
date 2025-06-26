package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultSignInControllerFactory;

public class DefaultSignInControllerFactoryTest {
    
    @Test
    void signInControllerFactoryShouldNotReturnNull() {
        DefaultSignInControllerFactory controllerFactory = new DefaultSignInControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
