package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.signup.SignUpController;

public class DefaultSignUpControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        return new SignUpController();
    }
    
}
