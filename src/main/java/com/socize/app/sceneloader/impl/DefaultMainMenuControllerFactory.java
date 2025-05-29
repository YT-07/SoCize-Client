package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.mainmenu.MainMenuController;

public class DefaultMainMenuControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        return new MainMenuController();
    }
    
}
