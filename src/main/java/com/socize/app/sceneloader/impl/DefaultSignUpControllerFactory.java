package com.socize.app.sceneloader.impl;

import com.socize.api.signup.DefaultSignUpApi;
import com.socize.api.signup.SignUpApi;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.TransitionablePage;
import com.socize.pages.signup.SignUpController;
import com.socize.pages.signup.impl.DefaultSignUpModel;
import com.socize.pages.signup.spi.SignUpModel;
import com.socize.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.MainMenuPageState;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.objectmapper.ObjectMapperProvider;

public class DefaultSignUpControllerFactory implements SceneControllerFactory {

    @Override
    public TransitionablePage createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();
        
        SignUpApi signUpApi = new DefaultSignUpApi();
        ObjectMapperProvider objectMapperProvider = DefaultObjectMapperProvider.getInstance();
        SignUpModel signUpModel = new DefaultSignUpModel(signUpApi, objectMapperProvider.getObjectMapper());

        return new SignUpController(mainMenuPageState, signUpModel);
    }
    
}
