package com.socize.shared.sessionid.spi;

public interface SessionIdManager {

    void setSessionId(String sessionId);
    String getSessionId();
}