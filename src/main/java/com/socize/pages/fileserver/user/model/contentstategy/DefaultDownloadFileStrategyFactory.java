package com.socize.pages.fileserver.user.model.contentstategy;

import com.socize.pages.fileserver.user.model.contentstategy.impl.JsonDownloadFileStrategy;
import com.socize.pages.fileserver.user.model.contentstategy.impl.OctetStreamDownloadFileStrategy;
import com.socize.pages.fileserver.user.model.contentstategy.spi.DownloadFileStrategy;

public class DefaultDownloadFileStrategyFactory implements DownloadFileStrategyFactory {

    @Override
    public DownloadFileStrategy getStrategyFor(String contentType) {
        
        // Null checks here to avoid unnecessary null pointer exception
        if(contentType == null) {
            return null;

        } else if(contentType.equalsIgnoreCase("application/json")) {
            return JsonDownloadFileStrategy.getInstance();

        } else if(contentType.equalsIgnoreCase("application/octet-stream")) {
            return OctetStreamDownloadFileStrategy.getInstance();

        } else {
            return null;
        }
    }
    
}
