package com.socize.pages.fileserver.user.model;

import com.socize.api.logout.dto.LogoutRequest;

public interface UserModel {

    /**
     * Logs the user out of the current session.
     * 
     * @param logoutRequest the logout request that contains all necessary data to logout.
     */
    void logout(LogoutRequest logoutRequest);
}