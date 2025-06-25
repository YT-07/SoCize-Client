package com.socize.api.getuseraccounts;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.getuseraccounts.dto.GetUserAccountsRequest;

public class DefaultGetUserAccountsApi implements GetUserAccountsApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultGetUserAccountsApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse getUserAccounts(GetUserAccountsRequest getAccountRequest) throws Exception {
        HttpPost request = new HttpPost("http://localhost/SoCize-Server/API/AdminViewUser.php");

        String json = objectMapper.writeValueAsString(getAccountRequest);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
