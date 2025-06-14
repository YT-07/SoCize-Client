package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.serverhealthcheck.DefaultServerHealthcheckApi;
import com.socize.api.serverhealthcheck.ServerHealthcheckApi;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.admin.serverhealthcheck.ServerHealthcheckController;
import com.socize.pages.fileserver.admin.serverhealthcheck.model.DefaultServerHealthcheckModel;
import com.socize.pages.fileserver.admin.serverhealthcheck.model.ServerHealthcheckModel;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.utilities.logoutservice.DefaultLogoutService;
import com.socize.pages.fileserver.utilities.logoutservice.LogoutService;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultServerHealthcheckControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        SessionManager sessionManager = DefaultSessionManager.getInstance();
        LogoutService logoutService = DefaultLogoutService.getInstance();
        TextStyler textStyler = DefaultTextStyler.getInstance();

        ObjectMapper objectMapper = DefaultObjectMapperProvider.getInstance().getObjectMapper();
        ServerHealthcheckApi serverHealthcheckApi = new DefaultServerHealthcheckApi();

        ServerHealthcheckModel serverHealthcheckModel = new DefaultServerHealthcheckModel(objectMapper, serverHealthcheckApi);

        return new ServerHealthcheckController(sessionManager, logoutService, serverHealthcheckModel, textStyler);
    }
    
}
