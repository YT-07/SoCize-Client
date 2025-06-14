package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.DefaultSceneProvider;
import com.socize.app.sceneprovider.SceneProvider;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.admin.AdminController;
import com.socize.pages.fileserver.admin.shared.adminpage.AdminPageStatusManager;
import com.socize.pages.fileserver.admin.shared.adminpage.DefaultAdminPageStatusManager;

public class DefaultAdminControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        AdminPageStatusManager adminPageStatusManager = DefaultAdminPageStatusManager.getInstance();
        SceneProvider sceneProvider = DefaultSceneProvider.getInstance();

        return new AdminController(adminPageStatusManager, sceneProvider);
    }
    
}
