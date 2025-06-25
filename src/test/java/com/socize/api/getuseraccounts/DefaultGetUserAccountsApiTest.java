package com.socize.api.getuseraccounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.getuseraccounts.dto.GetUserAccountsRequest;

public class DefaultGetUserAccountsApiTest {
    private ObjectMapper objectMapper;
    private CloseableHttpClient client;
    private GetUserAccountsRequest getUserAccountsRequest;

    private DefaultGetUserAccountsApi getUserAccountsApi;

    private static final String jsonData = "{\"key\": \"value\"}";

    @BeforeEach
    void init() throws Exception {
        client = mock(CloseableHttpClient.class);
        getUserAccountsRequest = mock(GetUserAccountsRequest.class);

        objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(getUserAccountsRequest)).thenReturn(jsonData);

        getUserAccountsApi = new DefaultGetUserAccountsApi(objectMapper, client);
    }

    @Test
    void shouldConstructJsonRequest() throws Exception {
        getUserAccountsApi.getUserAccounts(getUserAccountsRequest);
        verify(objectMapper, times(1)).writeValueAsString(getUserAccountsRequest);
    }

    @Test
    void shouldSetProvidedDataAsRequestEntity() throws Exception {
        getUserAccountsApi.getUserAccounts(getUserAccountsRequest);

        ArgumentCaptor<HttpPost> postRequestCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(client).execute(postRequestCaptor.capture());

        HttpPost capturedHttpPost = postRequestCaptor.getValue();
        String body = EntityUtils.toString(capturedHttpPost.getEntity(), StandardCharsets.UTF_8);

        assertEquals(jsonData, body);
    }

    @Test
    void shouldMakeHttpRequest() throws Exception {
        getUserAccountsApi.getUserAccounts(getUserAccountsRequest);
        verify(client, times(1)).execute(any(HttpPost.class));
    }
}
