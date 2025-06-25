package com.socize.api.signup;

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
import com.socize.api.signup.dto.SignUpRequest;

public class DefaultSignUpApiTest {
    private ObjectMapper objectMapper;
    private CloseableHttpClient client;
    private SignUpRequest signUpRequest;

    private DefaultSignUpApi signUpApi;

    private static final String jsonData = "{\"key\": \"value\"}";

    @BeforeEach
    void init() throws Exception {
        client = mock(CloseableHttpClient.class);
        signUpRequest = mock(SignUpRequest.class);

        objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(signUpRequest)).thenReturn(jsonData);

        signUpApi = new DefaultSignUpApi(objectMapper, client);
    }

    @Test
    void shouldConstructJsonRequest() throws Exception {
        signUpApi.signup(signUpRequest);
        verify(objectMapper, times(1)).writeValueAsString(signUpRequest);
    }

    @Test
    void shouldSetProvidedDataAsRequestEntity() throws Exception {
        signUpApi.signup(signUpRequest);

        ArgumentCaptor<HttpPost> postRequestCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(client).execute(postRequestCaptor.capture());

        HttpPost capturedHttpPost = postRequestCaptor.getValue();
        String body = EntityUtils.toString(capturedHttpPost.getEntity(), StandardCharsets.UTF_8);

        assertEquals(jsonData, body);
    }

    @Test
    void shouldMakeHttpRequest() throws Exception {
        signUpApi.signup(signUpRequest);
        verify(client, times(1)).execute(any(HttpPost.class));
    }
}
