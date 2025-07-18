package com.socize.api.getaccountdetails;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.getaccountdetails.dto.GetAccountDetailsRequest;

public class DefaultGetAccountDetailsApi implements GetAccountDetailsApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultGetAccountDetailsApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse getAccountDetails(GetAccountDetailsRequest getDetailsRequest) throws Exception {
        HttpPost request = new HttpPost("http://localhost/SoCize-Server/API/AccountDetails.php");

        String json = objectMapper.writeValueAsString(getDetailsRequest);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
