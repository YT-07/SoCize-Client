package com.socize.api.uploadfile;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.uploadfile.dto.UploadFileRequest;

public interface UploadFileApi {

    /**
     * Attempts to make an api call to upload the provided file to the server.
     * 
     * @param request the api request
     * @return the api response
     * @throws Exception if any exception occurs
     */
    CloseableHttpResponse uploadFile(UploadFileRequest request) throws Exception;
}