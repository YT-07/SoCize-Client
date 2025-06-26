package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultSignUpControllerFactory;

public class DefaultSignUpControllerFactoryTest {
    
    @Test
    void signUpControllerFactoryShouldNotReturnNull() {
        DefaultSignUpControllerFactory controllerFactory = new DefaultSignUpControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
