package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.deleteaccount.DefaultDeleteAccountApi;
import com.socize.api.deleteaccount.DeleteAccountApi;
import com.socize.api.getaccountdetails.DefaultGetAccountDetailsApi;
import com.socize.api.getaccountdetails.GetAccountDetailsApi;
import com.socize.api.getuseraccounts.DefaultGetUserAccountsApi;
import com.socize.api.getuseraccounts.GetUserAccountsApi;
import com.socize.api.utilities.httpclientprovider.DefaultHttpClientProvider;
import com.socize.api.utilities.httpclientprovider.HttpClientProvider;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.admin.usermanagement.UserManagementController;
import com.socize.pages.fileserver.admin.usermanagement.model.DefaultUserManagementModel;
import com.socize.pages.fileserver.admin.usermanagement.model.UserManagementModel;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.utilities.logoutservice.DefaultLogoutService;
import com.socize.pages.fileserver.utilities.logoutservice.LogoutService;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.textstyler.DefaultTextStyler;
import com.socize.utilities.textstyler.TextStyler;

public class DefaultUserManagementControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        SessionManager sessionManager = DefaultSessionManager.getInstance();
        TextStyler textStyler = DefaultTextStyler.getInstance();
        LogoutService logoutService = DefaultLogoutService.getInstance();
        ObjectMapper objectMapper = DefaultObjectMapperProvider.getInstance().getObjectMapper();
        HttpClientProvider httpClientProvider = DefaultHttpClientProvider.getInstance();

        GetUserAccountsApi getUserAccountsApi = new DefaultGetUserAccountsApi(objectMapper, httpClientProvider.getClient());
        GetAccountDetailsApi getAccountDetailsApi = new DefaultGetAccountDetailsApi();
        DeleteAccountApi deleteAccountApi = new DefaultDeleteAccountApi();

        UserManagementModel userManagementModel = new DefaultUserManagementModel(
            getUserAccountsApi, 
            objectMapper, 
            getAccountDetailsApi, 
            deleteAccountApi
        );

        return new UserManagementController(sessionManager, userManagementModel, textStyler, logoutService);
    }
    
}
