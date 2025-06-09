package com.socize.shared.sessionid.impl;

import com.socize.shared.sessionid.spi.SessionIdManager;

public class DefaultSessionIdManager implements SessionIdManager {
    private String sessionId;

    private DefaultSessionIdManager() {}

    private static class SingletonInstanceHolder {
        private static final DefaultSessionIdManager INSTANCE = new DefaultSessionIdManager();
    }

    public static DefaultSessionIdManager getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
    
}
