package com.socize.api.logout;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.logout.dto.LogoutRequest;

public interface LogoutApi {

    CloseableHttpResponse logout(LogoutRequest logoutRequest);
}