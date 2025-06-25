package com.socize.pages.fileserver.utilities.logoutservice;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.api.logout.DefaultLogoutApi;
import com.socize.api.logout.LogoutApi;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.api.utilities.httpclientprovider.DefaultHttpClientProvider;
import com.socize.api.utilities.httpclientprovider.HttpClientProvider;
import com.socize.pages.fileserver.shared.fileserverpage.DefaultFileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.fileserverpagestatus.DefaultFileServerPageStatus;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;
import com.socize.utilities.objectmapper.ObjectMapperProvider;

public class DefaultLogoutService implements LogoutService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultLogoutService.class);

    private final LogoutApi logoutApi;
    private final SessionManager sessionManager;
    private final FileServerPageManager fileServerPageManager;


    public DefaultLogoutService(LogoutApi logoutApi, SessionManager sessionManager, FileServerPageManager fileServerPageManager) {
        this.logoutApi = logoutApi;
        this.sessionManager = sessionManager;
        this.fileServerPageManager = fileServerPageManager;
    }

    private static class SingletonInstanceHolder {
        private static final DefaultLogoutService INSTANCE;

        static {
            ObjectMapperProvider objectMapperProvider = DefaultObjectMapperProvider.getInstance();
            HttpClientProvider httpClientProvider = DefaultHttpClientProvider.getInstance();
            SessionManager sessionManager = DefaultSessionManager.getInstance();
            FileServerPageManager fileServerPageManager = DefaultFileServerPageManager.getInstance();

            LogoutApi logoutApi = new DefaultLogoutApi(
                objectMapperProvider.getObjectMapper(), 
                httpClientProvider.getClient()
            );

            INSTANCE = new DefaultLogoutService(logoutApi, sessionManager, fileServerPageManager);
        }
    }

    public static DefaultLogoutService getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        try(CloseableHttpResponse response = logoutApi.logout(logoutRequest)) {
            HttpEntity entity = response.getEntity();

            if(entity != null) {
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            logger.error("Exception occured while closing http response for logout api.", e);
        }

        sessionManager.clearSession();
        fileServerPageManager.setStatus(DefaultFileServerPageStatus.AT_HOME);
    }
}
