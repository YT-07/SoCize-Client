package com.socize.pages.fileserver.shared.session;

public interface SessionManager {

    void setSession(String sessionId, String username);
    String getSessionId();
    String getUsername();
    void clearSession();
}