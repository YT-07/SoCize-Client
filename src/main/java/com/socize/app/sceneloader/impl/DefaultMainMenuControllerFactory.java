package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.SceneLoaders;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.app.sceneloader.spi.SceneLoader;
import com.socize.pages.mainmenu.MainMenuController;
import com.socize.shared.impl.DefaultMainMenuPageState;
import com.socize.shared.spi.MainMenuPageState;

public class DefaultMainMenuControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        MainMenuPageState pageState = DefaultMainMenuPageState.getInstance();
        SceneLoader loader = SceneLoaders.getDefault();

        return new MainMenuController(pageState, loader);
    }
    
}
