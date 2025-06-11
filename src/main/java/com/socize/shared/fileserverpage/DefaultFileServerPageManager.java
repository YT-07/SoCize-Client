package com.socize.shared.fileserverpage;

public class DefaultFileServerPageManager implements FileServerPageManager {
    private LoginStatus loginStatus;
    private final Object loginStatusLock;

    private DefaultFileServerPageManager() {
        loginStatus = LoginStatus.NOT_LOGGED_IN;
        loginStatusLock = new Object();
    }

    private static class SingletonInstanceHolder {
        private static final DefaultFileServerPageManager INSTANCE = new DefaultFileServerPageManager();
    }

    public static DefaultFileServerPageManager getInstance() {
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
