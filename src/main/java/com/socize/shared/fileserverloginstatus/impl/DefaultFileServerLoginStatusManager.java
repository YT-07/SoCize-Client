package com.socize.shared.fileserverloginstatus.impl;

import com.socize.shared.fileserverloginstatus.spi.FileServerLoginStatusManager;

public class DefaultFileServerLoginStatusManager implements FileServerLoginStatusManager {
    private LoginStatus loginStatus;
    private final Object loginStatusLock;

    private DefaultFileServerLoginStatusManager() {
        loginStatus = LoginStatus.NOT_LOGGED_IN;
        loginStatusLock = new Object();
    }

    private static class SingletonInstanceHolder {
        private static final DefaultFileServerLoginStatusManager INSTANCE = new DefaultFileServerLoginStatusManager();
    }

    public static DefaultFileServerLoginStatusManager getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void setLoginStatus(LoginStatus loginStatus) {
        
        synchronized(loginStatusLock) {
            this.loginStatus = loginStatus;
        }

    }

    @Override
    public LoginStatus getLoginStatus() {
        return loginStatus;
    }
    
}
