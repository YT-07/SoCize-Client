package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.DefaultSceneProvider;
import com.socize.app.sceneprovider.SceneProvider;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.mainmenu.MainMenuController;
import com.socize.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.MainMenuPageState;

public class DefaultMainMenuControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        MainMenuPageState pageState = DefaultMainMenuPageState.getInstance();
        SceneProvider provider = DefaultSceneProvider.getInstance();

        return new MainMenuController(pageState, provider);
    }
    
}
