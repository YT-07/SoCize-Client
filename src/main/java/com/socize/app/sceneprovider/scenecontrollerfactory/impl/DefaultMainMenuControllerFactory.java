package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.SceneLoader;
import com.socize.app.sceneprovider.SceneLoaders;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.TransitionablePage;
import com.socize.pages.mainmenu.MainMenuController;
import com.socize.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.MainMenuPageState;

public class DefaultMainMenuControllerFactory implements SceneControllerFactory {

    @Override
    public TransitionablePage createDefault() {
        MainMenuPageState pageState = DefaultMainMenuPageState.getInstance();
        SceneLoader loader = SceneLoaders.getDefault();

        return new MainMenuController(pageState, loader);
    }
    
}
