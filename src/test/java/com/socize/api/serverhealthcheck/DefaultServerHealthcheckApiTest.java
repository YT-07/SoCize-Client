package com.socize.api.serverhealthcheck;

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
import com.socize.api.serverhealthcheck.dto.ServerHealthcheckRequest;

public class DefaultServerHealthcheckApiTest {
    private ObjectMapper objectMapper;
    private CloseableHttpClient client;
    private ServerHealthcheckRequest serverHealthcheckRequest;

    private DefaultServerHealthcheckApi serverHealthcheckApi;

    private static final String jsonData = "{\"key\": \"value\"}";

    @BeforeEach
    void init() throws Exception {
        client = mock(CloseableHttpClient.class);
        serverHealthcheckRequest = mock(ServerHealthcheckRequest.class);

        objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(serverHealthcheckRequest)).thenReturn(jsonData);

        serverHealthcheckApi = new DefaultServerHealthcheckApi(objectMapper, client);
    }

    @Test
    void shouldConstructJsonRequest() throws Exception {
        serverHealthcheckApi.getServerStatus(serverHealthcheckRequest);
        verify(objectMapper, times(1)).writeValueAsString(serverHealthcheckRequest);
    }

    @Test
    void shouldSetProvidedDataAsRequestEntity() throws Exception {
        serverHealthcheckApi.getServerStatus(serverHealthcheckRequest);

        ArgumentCaptor<HttpPost> postRequestCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(client).execute(postRequestCaptor.capture());

        HttpPost capturedHttpPost = postRequestCaptor.getValue();
        String body = EntityUtils.toString(capturedHttpPost.getEntity(), StandardCharsets.UTF_8);

        assertEquals(jsonData, body);
    }

    @Test
    void shouldMakeHttpRequest() throws Exception {
        serverHealthcheckApi.getServerStatus(serverHealthcheckRequest);
        verify(client, times(1)).execute(any(HttpPost.class));
    }
}
