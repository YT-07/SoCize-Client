package com.socize.app.scenecontrollerfactory.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultAdminControllerFactory;

public class DefaultAdminControllerFactoryTest {
    
    @Test
    void adminControllerFactoryShouldNotReturnNull() {
        DefaultAdminControllerFactory controllerFactory = new DefaultAdminControllerFactory();
        assertNotNull(controllerFactory.createDefault());
    }
}
