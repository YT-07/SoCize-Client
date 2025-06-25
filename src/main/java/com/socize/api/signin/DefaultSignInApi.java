package com.socize.api.signin;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.signin.dto.SignInRequest;

public class DefaultSignInApi implements SignInApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultSignInApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse signin(SignInRequest signInRequest) throws Exception {
        HttpPost request = new HttpPost("localhost/SoCize-Server/API/login.php");

        String json = objectMapper.writeValueAsString(signInRequest);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
