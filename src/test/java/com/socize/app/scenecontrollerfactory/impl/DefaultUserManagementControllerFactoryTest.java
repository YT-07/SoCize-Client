package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultUserManagementControllerFactory;

public class DefaultUserManagementControllerFactoryTest {
    
    @Test
    void userManagementControllerFactoryShouldNotReturnNull() {
        DefaultUserManagementControllerFactory controllerFactory = new DefaultUserManagementControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
