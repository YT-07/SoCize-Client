package com.socize.api.serverhealthcheck;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.serverhealthcheck.dto.ServerHealthcheckRequest;

public interface ServerHealthcheckApi {

    /**
     * Attempts to make an api call to get the server status.
     * 
     * @param request the api request
     * @return the api response
     */
    CloseableHttpResponse getServerStatus(ServerHealthcheckRequest request);
}