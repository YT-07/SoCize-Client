package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultUserControllerFactory;

public class DefaultUserControllerFactoryTest {
    
    @Test
    void userControllerFactoryShouldNotReturnNull() {
        DefaultUserControllerFactory controllerFactory = new DefaultUserControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
