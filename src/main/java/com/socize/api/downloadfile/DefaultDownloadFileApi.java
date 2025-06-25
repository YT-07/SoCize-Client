package com.socize.api.downloadfile;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.downloadfile.dto.DownloadFileRequest;

public class DefaultDownloadFileApi implements DownloadFileApi {
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;

    public DefaultDownloadFileApi(ObjectMapper objectMapper, CloseableHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    @Override
    public CloseableHttpResponse downloadFile(DownloadFileRequest downloadRequest) throws Exception {
        HttpPost request = new HttpPost("http://localhost/SoCize-Server/API/FileDownload.php");

        String json = objectMapper.writeValueAsString(downloadRequest);

        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
