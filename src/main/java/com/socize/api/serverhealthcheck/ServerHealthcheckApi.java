package com.socize.api.serverhealthcheck;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.serverhealthcheck.dto.ServerHealthcheckRequest;

public interface ServerHealthcheckApi {

    /**
     * Attempts to make an api call to get the server status.
     * 
     * @param request the api request
     * @return the api response
     * @throws Exception if any exception occurs
     */
    CloseableHttpResponse getServerStatus(ServerHealthcheckRequest request) throws Exception;
}