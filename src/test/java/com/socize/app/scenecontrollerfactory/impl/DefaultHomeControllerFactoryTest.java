package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultHomeControllerFactory;

public class DefaultHomeControllerFactoryTest {
    
    @Test
    void homeControllerFactoryShouldNotReturnNull() {
        DefaultHomeControllerFactory controllerFactory = new DefaultHomeControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
