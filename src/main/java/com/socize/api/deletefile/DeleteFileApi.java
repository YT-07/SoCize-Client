package com.socize.api.deletefile;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.deletefile.dto.DeleteFileRequest;

public interface DeleteFileApi {

    /**
     * Attempts to make an api call to request for file deletion.
     * 
     * @param request the api request
     * @return the api response
     * @throws Exception if any exception occurs
     */
    CloseableHttpResponse deleteFile(DeleteFileRequest request) throws Exception;
}