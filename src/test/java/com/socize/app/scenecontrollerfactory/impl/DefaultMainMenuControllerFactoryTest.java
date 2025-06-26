package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultMainMenuControllerFactory;

public class DefaultMainMenuControllerFactoryTest {
    
    @Test
    void mainMenuControllerFactoryShouldNotReturnNull() {
        DefaultMainMenuControllerFactory controllerFactory = new DefaultMainMenuControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
