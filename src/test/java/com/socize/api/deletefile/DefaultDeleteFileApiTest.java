package com.socize.api.deletefile;

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
import com.socize.api.deletefile.dto.DeleteFileRequest;

public class DefaultDeleteFileApiTest {
    
    private ObjectMapper objectMapper;
    private CloseableHttpClient client;
    private DeleteFileRequest deleteFileRequest;

    @BeforeEach
    void init() {
        objectMapper = mock(ObjectMapper.class);
        client = mock(CloseableHttpClient.class);
        deleteFileRequest = mock(DeleteFileRequest.class);
    }

    @Test
    void shouldConstructJsonRequest() throws Exception {
        String expectedJson = "\"key\": \"value\"";
        when(objectMapper.writeValueAsString(deleteFileRequest)).thenReturn(expectedJson);

        DefaultDeleteFileApi deleteFileApi = new DefaultDeleteFileApi(objectMapper, client);
        deleteFileApi.deleteFile(deleteFileRequest);

        verify(objectMapper, times(1)).writeValueAsString(deleteFileRequest);
    }

    @Test
    void shouldSetProvidedDataAsRequestEntity() throws Exception {
        String expectedJson = "\"key\": \"value\"";
        when(objectMapper.writeValueAsString(deleteFileRequest)).thenReturn(expectedJson);

        DefaultDeleteFileApi deleteFileApi = new DefaultDeleteFileApi(objectMapper, client);
        deleteFileApi.deleteFile(deleteFileRequest);

        ArgumentCaptor<HttpPost> postRequestCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(client).execute(postRequestCaptor.capture());

        HttpPost capturedHttpPost = postRequestCaptor.getValue();
        String body = EntityUtils.toString(capturedHttpPost.getEntity(), StandardCharsets.UTF_8);

        assertEquals(expectedJson, body);
    }

    @Test
    void shouldMakeHttpRequest() throws Exception {
        String expectedJson = "\"key\": \"value\"";
        when(objectMapper.writeValueAsString(deleteFileRequest)).thenReturn(expectedJson);

        DefaultDeleteFileApi deleteFileApi = new DefaultDeleteFileApi(objectMapper, client);
        deleteFileApi.deleteFile(deleteFileRequest);

        verify(client, times(1)).execute(any(HttpPost.class));
    }
}
