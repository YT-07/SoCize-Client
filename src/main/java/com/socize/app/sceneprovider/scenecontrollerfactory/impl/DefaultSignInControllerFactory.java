package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.signin.DefaultSignInApi;
import com.socize.api.signin.SignInApi;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.signin.SignInController;
import com.socize.pages.fileserver.signin.model.DefaultSignInModel;
import com.socize.pages.fileserver.signin.model.SignInModel;
import com.socize.pages.mainmenu.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.pages.mainmenu.shared.mainmenupagestate.MainMenuPageState;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultSignInControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();
        TextStyler textStyler = DefaultTextStyler.getInstance();

        SignInApi signInApi = new DefaultSignInApi();
        ObjectMapper objectMapper = DefaultObjectMapperProvider.getInstance().getObjectMapper();
        
        SignInModel signInModel = new DefaultSignInModel(signInApi, objectMapper);

        SessionManager sessionManager = DefaultSessionManager.getInstance();

        return new SignInController(mainMenuPageState, textStyler, signInModel, sessionManager);
    }
    
}
