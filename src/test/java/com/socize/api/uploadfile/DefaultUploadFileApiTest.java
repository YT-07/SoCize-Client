package com.socize.api.uploadfile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;

import com.socize.api.uploadfile.dto.UploadFileRequest;

public class DefaultUploadFileApiTest {
    
    @TempDir
    private Path tempDir;

    private File tempFile;

    private CloseableHttpClient client;
    private UploadFileRequest uploadFileRequest;

    private DefaultUploadFileApi uploadFileApi;

    @BeforeEach
    void init() throws Exception {
        tempFile = tempDir.resolve("temp.txt").toFile();
        Files.write(tempFile.toPath(), "hello world".getBytes());

        client = mock(CloseableHttpClient.class);
        uploadFileRequest = new UploadFileRequest("session id", tempFile, "filename");

        uploadFileApi = new DefaultUploadFileApi(client);
    }

    @Test
    void requestBodyShouldBeMultipartFormData() throws Exception {
        uploadFileApi.uploadFile(uploadFileRequest);

        ArgumentCaptor<HttpPost> postRequestCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(client, times(1)).execute(postRequestCaptor.capture());

        HttpPost capturedHttpPost = postRequestCaptor.getValue();
        Header contentType = capturedHttpPost.getEntity().getContentType();

        // Multipart form data have 'boundary' as part of its header, so will only check if it contains 'multipart/form-data'
        assertTrue(contentType.getValue().contains("multipart/form-data"), "Expected multipart form data but got " + contentType.getValue());
    }

    @Test
    void shouldThrowException_IfFileToUploadIsNull() {
        UploadFileRequest nullFileRequest = new UploadFileRequest("session", null, "name");
        
        assertThrows(Exception.class, () -> {
            uploadFileApi.uploadFile(nullFileRequest);
        });
    }

    @Test
    void shouldMakeHttpRequest() throws Exception {
        uploadFileApi.uploadFile(uploadFileRequest);
        verify(client, times(1)).execute(any(HttpPost.class));
    }
}
