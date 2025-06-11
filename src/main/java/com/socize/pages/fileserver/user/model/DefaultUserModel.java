package com.socize.pages.fileserver.user.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.api.logout.dto.LogoutRequest;
import com.socize.pages.fileserver.utilities.logoutservice.LogoutService;

public class DefaultUserModel implements UserModel {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserModel.class);

    private final LogoutService logoutService;

    public DefaultUserModel(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        
        try {

            logoutService.logout(logoutRequest);

        } catch(Exception e) {
            logger.error("Something went wrong during logout.", e);
        }
    }
    
}
