package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.homepage.HomeController;
import com.socize.shared.impl.DefaultMainMenuPageState;
import com.socize.shared.spi.MainMenuPageState;

public class DefaultHomeControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();

        return new HomeController(mainMenuPageState);
    }
    
}
