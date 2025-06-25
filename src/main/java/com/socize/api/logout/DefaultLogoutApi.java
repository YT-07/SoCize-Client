package com.socize.api.logout;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.logout.dto.LogoutRequest;

public class DefaultLogoutApi implements LogoutApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultLogoutApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }
    
    @Override
    public CloseableHttpResponse logout(LogoutRequest logoutRequest) throws Exception {
        HttpPost request = new HttpPost("http://localhost/SoCize-Server/API/logout.php");

        String json = objectMapper.writeValueAsString(logoutRequest);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
