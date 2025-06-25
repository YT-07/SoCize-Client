package com.socize.api.deletefile;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.deletefile.dto.DeleteFileRequest;

public class DefaultDeleteFileApi implements DeleteFileApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultDeleteFileApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse deleteFile(DeleteFileRequest deleteRequest) throws Exception {
        HttpPost request = new HttpPost("http://localhost/SoCize-Server/API/DeleteFileData.php");

        String json = objectMapper.writeValueAsString(deleteRequest);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
