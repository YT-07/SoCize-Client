package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.user.UserController;

public class DefaultUserControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        return new UserController();
    }
    
}
