package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.admin.usermanagement.UserManagementController;

public class DefaultUserManagementControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        return new UserManagementController();
    }
    
}
