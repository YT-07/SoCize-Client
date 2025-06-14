package com.socize.api.serverhealthcheck;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.serverhealthcheck.dto.ServerHealthcheckRequest;

public class DefaultServerHealthcheckApi implements ServerHealthcheckApi {

    @Override
    public CloseableHttpResponse getServerStatus(ServerHealthcheckRequest request) {
        // TODO

        return null;
    }
    
}
