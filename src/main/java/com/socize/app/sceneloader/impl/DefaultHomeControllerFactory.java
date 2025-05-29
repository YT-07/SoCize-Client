package com.socize.app.sceneloader.impl;

import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.pages.homepage.HomeController;

public class DefaultHomeControllerFactory implements SceneControllerFactory {

    @Override
    public Object createDefault() {
        return new HomeController();
    }
    
}
