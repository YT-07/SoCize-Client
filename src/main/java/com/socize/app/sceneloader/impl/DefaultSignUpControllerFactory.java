package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.signup.SignUpController;
import com.socize.shared.mainmenupagestate.impl.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.spi.MainMenuPageState;

public class DefaultSignUpControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();

        return new SignUpController(mainMenuPageState);
    }
    
}
