package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.api.signup.DefaultSignUpApi;
import com.socize.api.signup.SignUpApi;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.signup.SignUpController;
import com.socize.pages.fileserver.signup.model.DefaultSignUpModel;
import com.socize.pages.fileserver.signup.model.SignUpModel;
import com.socize.pages.mainmenu.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.pages.mainmenu.shared.mainmenupagestate.MainMenuPageState;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.objectmapper.ObjectMapperProvider;

public class DefaultSignUpControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();
        
        SignUpApi signUpApi = new DefaultSignUpApi();
        ObjectMapperProvider objectMapperProvider = DefaultObjectMapperProvider.getInstance();
        SignUpModel signUpModel = new DefaultSignUpModel(signUpApi, objectMapperProvider.getObjectMapper());

        return new SignUpController(mainMenuPageState, signUpModel);
    }
    
}
