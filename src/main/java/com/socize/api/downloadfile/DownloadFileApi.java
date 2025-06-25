package com.socize.api.downloadfile;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.downloadfile.dto.DownloadFileRequest;

public interface DownloadFileApi {
    
    /**
     * Attempts to make an api call to request for a file to be downloaded.
     * 
     * @param request the api request
     * @return the api response
     * @throws Exception if any exception occurs
     */
    CloseableHttpResponse downloadFile(DownloadFileRequest request) throws Exception;
}
