package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.admin.AdminController;

public class DefaultAdminControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        return new AdminController();
    }
    
}
