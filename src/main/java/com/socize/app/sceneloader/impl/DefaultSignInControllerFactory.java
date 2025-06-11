package com.socize.app.sceneloader.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.signin.DefaultSignInApi;
import com.socize.api.signin.SignInApi;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.TransitionablePage;
import com.socize.pages.signin.SignInController;
import com.socize.pages.signin.impl.DefaultSignInModel;
import com.socize.pages.signin.spi.SignInModel;
import com.socize.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.MainMenuPageState;
import com.socize.shared.session.impl.DefaultSessionManager;
import com.socize.shared.session.spi.SessionManager;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultSignInControllerFactory implements SceneControllerFactory {

    @Override
    public TransitionablePage createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();
        TextStyler textStyler = DefaultTextStyler.getInstance();

        SignInApi signInApi = new DefaultSignInApi();
        ObjectMapper objectMapper = DefaultObjectMapperProvider.getInstance().getObjectMapper();
        
        SignInModel signInModel = new DefaultSignInModel(signInApi, objectMapper);

        SessionManager sessionManager = DefaultSessionManager.getInstance();

        return new SignInController(mainMenuPageState, textStyler, signInModel, sessionManager);
    }
    
}
