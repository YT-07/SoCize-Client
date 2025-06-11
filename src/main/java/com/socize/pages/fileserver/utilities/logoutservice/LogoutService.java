package com.socize.pages.fileserver.utilities.logoutservice;

import com.socize.api.logout.dto.LogoutRequest;

public interface LogoutService {
    
    void logout(LogoutRequest logoutRequest);
}
