package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.homepage.HomeController;
import com.socize.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.MainMenuPageState;

public class DefaultHomeControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        MainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();

        return new HomeController(mainMenuPageState);
    }
    
}
