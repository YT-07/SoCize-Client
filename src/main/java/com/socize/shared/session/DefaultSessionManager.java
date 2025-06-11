package com.socize.shared.session;

public class DefaultSessionManager implements SessionManager {
    private String sessionId;
    private String username;
    private final Object sessionLock;

    private DefaultSessionManager() {
        sessionLock = new Object();
    }

    private static class SingletonInstanceHolder {
        private static final DefaultSessionManager INSTANCE = new DefaultSessionManager();
    }

    public static DefaultSessionManager getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void setSession(String sessionId, String username) {
        
        synchronized(sessionLock) {
            this.sessionId = sessionId;
            this.username = username;
        }
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void clearSession() {
        
        synchronized(sessionLock) {
            this.sessionId = null;
            this.username = null;
        }
    }
    
}
