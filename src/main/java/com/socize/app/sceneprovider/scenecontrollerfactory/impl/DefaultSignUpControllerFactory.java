package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.api.signup.DefaultSignUpApi;
import com.socize.api.signup.SignUpApi;
import com.socize.api.utilities.httpclientprovider.DefaultHttpClientProvider;
import com.socize.api.utilities.httpclientprovider.HttpClientProvider;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.fileserverpage.DefaultFileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;
import com.socize.pages.fileserver.signup.SignUpController;
import com.socize.pages.fileserver.signup.model.DefaultSignUpModel;
import com.socize.pages.fileserver.signup.model.SignUpModel;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.objectmapper.ObjectMapperProvider;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultSignUpControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        FileServerPageManager fileServerPageManager = DefaultFileServerPageManager.getInstance();
        
        ObjectMapperProvider objectMapperProvider = DefaultObjectMapperProvider.getInstance();
        HttpClientProvider httpClientProvider = DefaultHttpClientProvider.getInstance();

        SignUpApi signUpApi = new DefaultSignUpApi(objectMapperProvider.getObjectMapper(), httpClientProvider.getClient());
        SignUpModel signUpModel = new DefaultSignUpModel(signUpApi, objectMapperProvider.getObjectMapper());
        TextStyler textStyler = DefaultTextStyler.getInstance();

        return new SignUpController(fileServerPageManager, signUpModel, textStyler);
    }
    
}
