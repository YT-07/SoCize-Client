package com.socize.pages.fileserver.shared.session;

public interface SessionManager {

    /**
     * Sets the current session's data.
     * 
     * @param sessionId the session id
     * @param username the username of the user
     */
    void setSession(String sessionId, String username);

    /**
     * Gets the current session's id.
     * 
     * @return the session id
     */
    String getSessionId();

    /**
     * Gets the current session's username.
     * 
     * @return the username
     */
    String getUsername();

    /**
     * Clears all session data.
     */
    void clearSession();
}