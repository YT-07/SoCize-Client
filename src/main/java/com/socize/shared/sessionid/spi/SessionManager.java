package com.socize.shared.sessionid.spi;

public interface SessionManager {

    void setSession(String sessionId, String username);
    String getSessionId();
    String getUsername();
}