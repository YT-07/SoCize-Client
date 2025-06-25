package com.socize.api.deleteaccount;

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
import com.socize.api.deleteaccount.dto.DeleteAccountRequest;

public class DefaultDeleteAccountApiTest {
    
    private DeleteAccountRequest deleteAccountRequest;
    private ObjectMapper objectMapper;
    private CloseableHttpClient client;

    @BeforeEach
    void init() {
        deleteAccountRequest = mock(DeleteAccountRequest.class);
        objectMapper = mock(ObjectMapper.class);
        client = mock(CloseableHttpClient.class);
    }

    @Test
    void shouldConstructJsonRequest() throws Exception {
        when(objectMapper.writeValueAsString(deleteAccountRequest)).thenReturn("{\"key\": \"value\"}");

        DefaultDeleteAccountApi deleteAccountApi = new DefaultDeleteAccountApi(objectMapper, client);
        deleteAccountApi.deleteAccount(deleteAccountRequest);

        verify(objectMapper, times(1)).writeValueAsString(deleteAccountRequest);
    }

    @Test
    void shouldSetProvidedDataAsRequestEntity() throws Exception {
        String expectedJson = "\"key\": \"value\"";
        when(objectMapper.writeValueAsString(deleteAccountRequest)).thenReturn(expectedJson);

        DefaultDeleteAccountApi deleteAccountApi = new DefaultDeleteAccountApi(objectMapper, client);
        deleteAccountApi.deleteAccount(deleteAccountRequest);

        ArgumentCaptor<HttpPost> postRequestCaptor =ArgumentCaptor.forClass(HttpPost.class);
        verify(client).execute(postRequestCaptor.capture());

        HttpPost postRequest = postRequestCaptor.getValue();
        String body = EntityUtils.toString(postRequest.getEntity(), StandardCharsets.UTF_8);

        assertEquals(expectedJson, body);
    }

    @Test
    void shouldMakeHttpRequest() throws Exception {
        String expectedJson = "\"key\": \"value\"";
        when(objectMapper.writeValueAsString(deleteAccountRequest)).thenReturn(expectedJson);

        DefaultDeleteAccountApi deleteAccountApi = new DefaultDeleteAccountApi(objectMapper, client);
        deleteAccountApi.deleteAccount(deleteAccountRequest);

        verify(client, times(1)).execute(any(HttpPost.class));
    }
}
