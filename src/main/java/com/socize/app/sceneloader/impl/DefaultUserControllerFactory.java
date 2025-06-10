package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.TransitionablePage;
import com.socize.pages.user.UserController;

public class DefaultUserControllerFactory implements SceneControllerFactory {

    @Override
    public TransitionablePage createDefault() {
        return new UserController();
    }
    
}
