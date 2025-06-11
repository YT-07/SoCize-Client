package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.DefaultSceneProvider;
import com.socize.app.sceneprovider.SceneProvider;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.fileserverpage.DefaultFileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;
import com.socize.pages.mainmenu.MainMenuController;
import com.socize.pages.mainmenu.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.pages.mainmenu.shared.mainmenupagestate.MainMenuPageState;

public class DefaultMainMenuControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        MainMenuPageState pageState = DefaultMainMenuPageState.getInstance();
        SceneProvider provider = DefaultSceneProvider.getInstance();
        FileServerPageManager fileServerPageManager = DefaultFileServerPageManager.getInstance();

        return new MainMenuController(pageState, provider, fileServerPageManager);
    }
    
}
