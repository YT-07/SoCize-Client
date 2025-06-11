package com.socize.pages.fileserver.user.model;

import com.socize.api.logout.dto.LogoutRequest;

public interface UserModel {

    void logout(LogoutRequest logoutRequest);
}