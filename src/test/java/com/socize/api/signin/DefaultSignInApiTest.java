package com.socize.api.signin;

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
import com.socize.api.signin.dto.SignInRequest;

public class DefaultSignInApiTest {
    private ObjectMapper objectMapper;
    private CloseableHttpClient client;
    private SignInRequest signInRequest;

    private DefaultSignInApi signInApi;

    private static final String jsonData = "{\"key\": \"value\"}";

    @BeforeEach
    void init() throws Exception {
        client = mock(CloseableHttpClient.class);
        signInRequest = mock(SignInRequest.class);

        objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(signInRequest)).thenReturn(jsonData);

        signInApi = new DefaultSignInApi(objectMapper, client);
    }

    @Test
    void shouldConstructJsonRequest() throws Exception {
        signInApi.signin(signInRequest);
        verify(objectMapper, times(1)).writeValueAsString(signInRequest);
    }

    @Test
    void shouldSetProvidedDataAsRequestEntity() throws Exception {
        signInApi.signin(signInRequest);

        ArgumentCaptor<HttpPost> postRequestCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(client).execute(postRequestCaptor.capture());

        HttpPost capturedHttpPost = postRequestCaptor.getValue();
        String body = EntityUtils.toString(capturedHttpPost.getEntity(), StandardCharsets.UTF_8);

        assertEquals(jsonData, body);
    }

    @Test
    void shouldMakeHttpRequest() throws Exception {
        signInApi.signin(signInRequest);
        verify(client, times(1)).execute(any(HttpPost.class));
    }
}
