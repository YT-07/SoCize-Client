package com.socize.pages.fileserver.utilities.logoutservice;

import com.socize.api.logout.DefaultLogoutApi;
import com.socize.api.logout.LogoutApi;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.pages.fileserver.shared.fileserverpage.DefaultFileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.fileserverpagestatus.DefaultFileServerPageStatus;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;

public class DefaultLogoutService implements LogoutService {
    private final LogoutApi logoutApi;
    private final SessionManager sessionManager;
    private final FileServerPageManager fileServerPageManager;


    public DefaultLogoutService(LogoutApi logoutApi, SessionManager sessionManager, FileServerPageManager fileServerPageManager) {
        this.logoutApi = logoutApi;
        this.sessionManager = sessionManager;
        this.fileServerPageManager = fileServerPageManager;
    }

    private static class SingletonInstanceHolder {
        private static final DefaultLogoutService INSTANCE = new DefaultLogoutService(
            new DefaultLogoutApi(),
            DefaultSessionManager.getInstance(),
            DefaultFileServerPageManager.getInstance()
        );
    }

    public static DefaultLogoutService getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        logoutApi.logout(logoutRequest);
        sessionManager.clearSession();
        fileServerPageManager.setStatus(DefaultFileServerPageStatus.AT_HOME);
    }
}
