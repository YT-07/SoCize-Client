package com.socize.pages.fileserver.user.model.contentstategy.spi;

import java.io.File;

import org.apache.http.HttpEntity;

import com.socize.pages.fileserver.user.dto.DownloadFileResult;

public interface DownloadFileStrategy {

    /**
     * Handles the processing of the http response when downloading a file 
     * based on its content type.
     * 
     * @param entity the http entity received
     * @param pathToSave the file path to save the downloaded file
     * @return the result of the response processing
     * @throws Exception if any exception occurs
     */
    DownloadFileResult handle(HttpEntity entity, File pathToSave) throws Exception ;
}