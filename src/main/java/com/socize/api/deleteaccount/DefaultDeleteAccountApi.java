package com.socize.api.deleteaccount;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.deleteaccount.dto.DeleteAccountRequest;

public class DefaultDeleteAccountApi implements DeleteAccountApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultDeleteAccountApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse deleteAccount(DeleteAccountRequest deleteRequest) throws Exception {
        HttpPost request = new HttpPost("localhost/SoCize-Server/API/DeleteUser.php");

        String json = objectMapper.writeValueAsString(deleteRequest);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
