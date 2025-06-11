package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.admin.AdminController;

public class DefaultAdminControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        return new AdminController();
    }
    
}
