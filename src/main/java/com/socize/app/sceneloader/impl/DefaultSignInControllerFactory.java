package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.signin.SignInController;

public class DefaultSignInControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        return new SignInController();
    }
    
}
