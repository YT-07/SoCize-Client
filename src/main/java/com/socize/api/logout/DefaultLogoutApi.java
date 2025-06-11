package com.socize.api.logout;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.logout.dto.LogoutRequest;

public class DefaultLogoutApi implements LogoutApi {

    @Override
    public CloseableHttpResponse logout(LogoutRequest logoutRequest) {
        // TODO

        return null;
    }
    
}
