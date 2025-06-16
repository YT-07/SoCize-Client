package com.socize.pages.fileserver.user.model.contentstategy;

import com.socize.pages.fileserver.user.model.contentstategy.spi.DownloadFileStrategy;

public interface DownloadFileStrategyFactory {

    /**
     * Gets a file download strategy based on the {@cde content type}, 
     * or {@code null} if no strategy exists for the given content type.
     * 
     * @param contentType the content type of the file download http response
     * @return the strategy to use, or {@code null} if no such strategy exists
     */
    DownloadFileStrategy getStrategyFor(String contentType);
}