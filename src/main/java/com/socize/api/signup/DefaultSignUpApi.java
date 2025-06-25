package com.socize.api.signup;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.signup.dto.SignUpRequest;

public class DefaultSignUpApi implements SignUpApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultSignUpApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse signup(SignUpRequest signUpRequest) throws Exception {
        HttpPost request = new HttpPost("localhost/SoCize-Server/API/SignUp.php");

        String json = objectMapper.writeValueAsString(request);
        
        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
