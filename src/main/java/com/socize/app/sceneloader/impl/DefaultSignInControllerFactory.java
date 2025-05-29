package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.signin.SignInController;
import com.socize.shared.impl.DefaultMainMenuPageState;
import com.socize.shared.spi.MainMenuPageState;

public class DefaultSignInControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();

        return new SignInController(mainMenuPageState);
    }
    
}
