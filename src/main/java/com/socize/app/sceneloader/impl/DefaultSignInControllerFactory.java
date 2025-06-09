package com.socize.app.sceneloader.impl;

import com.socize.api.signin.impl.DefaultSignInApi;
import com.socize.api.signin.spi.SignInApi;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.signin.SignInController;
import com.socize.shared.mainmenupagestate.impl.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.spi.MainMenuPageState;

public class DefaultSignInControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();
        SignInApi signInApi = new DefaultSignInApi();

        return new SignInController(mainMenuPageState, signInApi);
    }
    
}
