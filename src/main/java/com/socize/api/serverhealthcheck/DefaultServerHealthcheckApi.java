package com.socize.api.serverhealthcheck;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.serverhealthcheck.dto.ServerHealthcheckRequest;

public class DefaultServerHealthcheckApi implements ServerHealthcheckApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultServerHealthcheckApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse getServerStatus(ServerHealthcheckRequest healthcheckRequest) throws Exception {
        HttpPost request = new HttpPost("http://localhost/SoCize-Server/API/ServerHealth.php");

        String json = objectMapper.writeValueAsString(healthcheckRequest);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
