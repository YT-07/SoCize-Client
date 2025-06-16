package com.socize.pages.fileserver.user.model.contentstategy.impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.http.HttpEntity;

import com.socize.pages.fileserver.user.dto.DownloadFileResult;
import com.socize.pages.fileserver.user.model.contentstategy.spi.DownloadFileStrategy;

public class OctetStreamDownloadFileStrategy implements DownloadFileStrategy {

    private OctetStreamDownloadFileStrategy() {};

    private static class SigletonInstanceHolder {
        private static final OctetStreamDownloadFileStrategy INSTANCE = new OctetStreamDownloadFileStrategy();
    }

    public static OctetStreamDownloadFileStrategy getInstance() {
        return SigletonInstanceHolder.INSTANCE;
    }

    @Override
    public DownloadFileResult handle(HttpEntity entity, File pathToSave) throws Exception {
        InputStream inputStream = entity.getContent();
        Files.copy(inputStream, pathToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return new DownloadFileResult(true, null);
    }
    
}
