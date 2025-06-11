package com.socize.pages.fileserver.utilities.logoutservice;

import com.socize.api.logout.dto.LogoutRequest;

public interface LogoutService {
    
    /**
     * Logs the user out of the current session and perform any necessary clean up operation.
     * 
     * @param logoutRequest the logout request that contains all necessary data for logging out
     */
    void logout(LogoutRequest logoutRequest);
}
