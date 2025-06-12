package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.api.getdownloadablefiles.DefaultGetDownloadableFilesApi;
import com.socize.api.getdownloadablefiles.GetDownloadableFilesApi;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.user.UserController;
import com.socize.pages.fileserver.user.model.DefaultUserModel;
import com.socize.pages.fileserver.user.model.UserModel;
import com.socize.pages.fileserver.utilities.logoutservice.DefaultLogoutService;
import com.socize.pages.fileserver.utilities.logoutservice.LogoutService;

public class DefaultUserControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        SessionManager sessionManager = DefaultSessionManager.getInstance();

        LogoutService logoutService = DefaultLogoutService.getInstance();
        GetDownloadableFilesApi getDownloadableFilesApi = new DefaultGetDownloadableFilesApi();

        UserModel userModel = new DefaultUserModel(logoutService, getDownloadableFilesApi);

        return new UserController(sessionManager, userModel);
    }
    
}
