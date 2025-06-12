package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultUserControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        SessionManager sessionManager = DefaultSessionManager.getInstance();
        TextStyler textStyler = DefaultTextStyler.getInstance();

        LogoutService logoutService = DefaultLogoutService.getInstance();
        GetDownloadableFilesApi getDownloadableFilesApi = new DefaultGetDownloadableFilesApi();
        ObjectMapper objectMapper = DefaultObjectMapperProvider.getInstance().getObjectMapper();

        UserModel userModel = new DefaultUserModel(logoutService, getDownloadableFilesApi, objectMapper);

        return new UserController(sessionManager, userModel, textStyler);
    }
    
}
