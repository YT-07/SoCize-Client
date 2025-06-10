package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.SceneLoaders;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.app.sceneloader.spi.SceneLoader;
import com.socize.pages.TransitionablePage;
import com.socize.pages.mainmenu.MainMenuController;
import com.socize.shared.mainmenupagestate.impl.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.spi.MainMenuPageState;

public class DefaultMainMenuControllerFactory implements SceneControllerFactory {

    @Override
    public TransitionablePage createDefault() {
        MainMenuPageState pageState = DefaultMainMenuPageState.getInstance();
        SceneLoader loader = SceneLoaders.getDefault();

        return new MainMenuController(pageState, loader);
    }
    
}
