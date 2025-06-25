package com.socize.api.getdownloadablefiles;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.getdownloadablefiles.dto.GetDownloadableFilesRequest;

public interface GetDownloadableFilesApi {

    /**
     * Attempts to make an api call to retrieve all the downloadable files 
     * for the current user.
     * 
     * @param getDownloadableFilesRequest the request containing all necessary data to retrieve all downloadable files
     * @return the api response
     * @throws Exception if any exception occurs
     */
    CloseableHttpResponse getDownloadableFiles(GetDownloadableFilesRequest getDownloadableFilesRequest) throws Exception;
}