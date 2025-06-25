package com.socize.api.getdownloadablefiles;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.getdownloadablefiles.dto.GetDownloadableFilesRequest;

public class DefaultGetDownloadableFilesApi implements GetDownloadableFilesApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultGetDownloadableFilesApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse getDownloadableFiles(GetDownloadableFilesRequest getDownloadableFilesRequest) throws Exception {
        HttpPost request = new HttpPost("localhost/SoCize-Server/API/GetFileRecords.php");

        String json = objectMapper.writeValueAsString(getDownloadableFilesRequest);
        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
