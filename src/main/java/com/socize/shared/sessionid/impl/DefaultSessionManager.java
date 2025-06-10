package com.socize.shared.sessionid.impl;

import com.socize.shared.sessionid.spi.SessionManager;

public class DefaultSessionManager implements SessionManager {
    private String sessionId;
    private String username;

    private DefaultSessionManager() {}

    private static class SingletonInstanceHolder {
        private static final DefaultSessionManager INSTANCE = new DefaultSessionManager();
    }

    public static DefaultSessionManager getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public synchronized void setSession(String sessionId, String username) {
        this.sessionId = sessionId;
        this.username = username;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
}
