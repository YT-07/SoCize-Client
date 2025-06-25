package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.signin.DefaultSignInApi;
import com.socize.api.signin.SignInApi;
import com.socize.api.utilities.httpclientprovider.DefaultHttpClientProvider;
import com.socize.api.utilities.httpclientprovider.HttpClientProvider;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.fileserverpage.DefaultFileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.signin.SignInController;
import com.socize.pages.fileserver.signin.model.DefaultSignInModel;
import com.socize.pages.fileserver.signin.model.SignInModel;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultSignInControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        FileServerPageManager fileServerPageManager = DefaultFileServerPageManager.getInstance();
        TextStyler textStyler = DefaultTextStyler.getInstance();

        ObjectMapper objectMapper = DefaultObjectMapperProvider.getInstance().getObjectMapper();
        HttpClientProvider httpClientProvider = DefaultHttpClientProvider.getInstance();

        SignInApi signInApi = new DefaultSignInApi(objectMapper, httpClientProvider.getClient());
        SignInModel signInModel = new DefaultSignInModel(signInApi, objectMapper);

        SessionManager sessionManager = DefaultSessionManager.getInstance();

        return new SignInController(fileServerPageManager, textStyler, signInModel, sessionManager);
    }
    
}
