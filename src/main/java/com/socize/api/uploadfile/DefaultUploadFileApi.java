package com.socize.api.uploadfile;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;

import com.socize.api.uploadfile.dto.UploadFileRequest;

public class DefaultUploadFileApi implements UploadFileApi {
    private final CloseableHttpClient client;

    public DefaultUploadFileApi(CloseableHttpClient client) {
        this.client = client;
    }

    @Override
    public CloseableHttpResponse uploadFile(UploadFileRequest uploadRequest) throws Exception {
        HttpPost request = new HttpPost("http://localhost/SoCize-Server/API/FileUpload.php");

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

        File fileToUpload = uploadRequest.fileToUpload();
        FileBody fileBody = new FileBody(fileToUpload, ContentType.APPLICATION_OCTET_STREAM, fileToUpload.getName());
        entityBuilder.addPart("file", fileBody);

        entityBuilder.addTextBody("sessionId", uploadRequest.sessionId(), ContentType.TEXT_PLAIN);
        entityBuilder.addTextBody("newFileName", uploadRequest.filename(), ContentType.TEXT_PLAIN);

        HttpEntity entity = entityBuilder.build();
        request.setEntity(entity);

        return client.execute(request);
    }
    
}
