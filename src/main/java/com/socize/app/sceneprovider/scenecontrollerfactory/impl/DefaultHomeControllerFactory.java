package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.homepage.HomeController;
import com.socize.pages.fileserver.shared.fileserverpage.DefaultFileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;

public class DefaultHomeControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        FileServerPageManager fileServerPageManager = DefaultFileServerPageManager.getInstance();

        return new HomeController(fileServerPageManager);
    }
    
}
