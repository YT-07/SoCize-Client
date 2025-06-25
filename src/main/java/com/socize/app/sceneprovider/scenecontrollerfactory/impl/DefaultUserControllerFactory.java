package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.deletefile.DefaultDeleteFileApi;
import com.socize.api.deletefile.DeleteFileApi;
import com.socize.api.downloadfile.DefaultDownloadFileApi;
import com.socize.api.downloadfile.DownloadFileApi;
import com.socize.api.getdownloadablefiles.DefaultGetDownloadableFilesApi;
import com.socize.api.getdownloadablefiles.GetDownloadableFilesApi;
import com.socize.api.uploadfile.DefaultUploadFileApi;
import com.socize.api.uploadfile.UploadFileApi;
import com.socize.api.utilities.httpclientprovider.DefaultHttpClientProvider;
import com.socize.api.utilities.httpclientprovider.HttpClientProvider;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.user.UserController;
import com.socize.pages.fileserver.user.model.DefaultUserModel;
import com.socize.pages.fileserver.user.model.UserModel;
import com.socize.pages.fileserver.user.model.contentstategy.DefaultDownloadFileStrategyFactory;
import com.socize.pages.fileserver.user.model.contentstategy.DownloadFileStrategyFactory;
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
        ObjectMapper objectMapper = DefaultObjectMapperProvider.getInstance().getObjectMapper();
        DownloadFileStrategyFactory downloadFileStrategyFactory = new DefaultDownloadFileStrategyFactory();
        HttpClientProvider httpClientProvider = DefaultHttpClientProvider.getInstance();

        GetDownloadableFilesApi getDownloadableFilesApi = new DefaultGetDownloadableFilesApi(objectMapper, httpClientProvider.getClient());
        DeleteFileApi deleteFileApi = new DefaultDeleteFileApi(objectMapper, httpClientProvider.getClient());
        DownloadFileApi downloadFileApi = new DefaultDownloadFileApi(objectMapper, httpClientProvider.getClient());

        UploadFileApi uploadFileApi = new DefaultUploadFileApi(httpClientProvider.getClient());

        UserModel userModel = new DefaultUserModel(
            logoutService, 
            getDownloadableFilesApi, 
            objectMapper, 
            deleteFileApi,
            downloadFileApi,
            uploadFileApi,
            downloadFileStrategyFactory
        );

        return new UserController(sessionManager, userModel, textStyler);
    }
    
}
