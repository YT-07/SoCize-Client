package com.socize.api.logout;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.logout.dto.LogoutRequest;

public interface LogoutApi {

    /**
     * Attempts to make an api call to log the user out of their session.
     * 
     * @param logoutRequest the logout request containing necessary data to logout
     * @return the api response
     * @throws Exception if any exception occur
     */
    CloseableHttpResponse logout(LogoutRequest logoutRequest) throws Exception;
}