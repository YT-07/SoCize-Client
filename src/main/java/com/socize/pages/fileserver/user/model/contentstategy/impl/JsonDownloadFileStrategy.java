package com.socize.pages.fileserver.user.model.contentstategy.impl;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.pages.fileserver.user.dto.DownloadFileResult;
import com.socize.pages.fileserver.user.model.contentstategy.spi.DownloadFileStrategy;
import com.socize.utilities.objectmapper.DefaultObjectMapperProvider;

public class JsonDownloadFileStrategy implements DownloadFileStrategy {
    private final ObjectMapper objectMapper;

    private JsonDownloadFileStrategy(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static class SingletonInstanceHolder {
        private static final JsonDownloadFileStrategy INSTANCE;

        static {
            ObjectMapper objectMapper = DefaultObjectMapperProvider.getInstance().getObjectMapper();
            INSTANCE = new JsonDownloadFileStrategy(objectMapper);
        }
    }

    public static JsonDownloadFileStrategy getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public DownloadFileResult handle(HttpEntity entity, File pathToSave) throws Exception {
        String json = EntityUtils.toString(entity);
        return objectMapper.readValue(json, DownloadFileResult.class);

    }
    
}