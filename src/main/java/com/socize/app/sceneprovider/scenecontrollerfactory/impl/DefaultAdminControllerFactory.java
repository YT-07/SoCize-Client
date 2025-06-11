package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.TransitionablePage;
import com.socize.pages.admin.AdminController;

public class DefaultAdminControllerFactory implements SceneControllerFactory {

    @Override
    public TransitionablePage createDefault() {
        return new AdminController();
    }
    
}
