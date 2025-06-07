package com.socize.app.sceneloader.impl;

import com.socize.api.impl.DefaultSignUpApi;
import com.socize.api.spi.SignUpApi;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.signup.SignUpController;
import com.socize.pages.signup.impl.DefaultSignUpModel;
import com.socize.pages.signup.spi.SignUpModel;
import com.socize.shared.mainmenupagestate.impl.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.spi.MainMenuPageState;
import com.socize.utilities.objectmapper.impl.DefaultObjectMapperProvider;
import com.socize.utilities.objectmapper.spi.ObjectMapperProvider;

public class DefaultSignUpControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();
        
        SignUpApi signUpApi = new DefaultSignUpApi();
        ObjectMapperProvider objectMapperProvider = DefaultObjectMapperProvider.getInstance();
        SignUpModel signUpModel = new DefaultSignUpModel(signUpApi, objectMapperProvider.getObjectMapper());

        return new SignUpController(mainMenuPageState, signUpModel);
    }
    
}
