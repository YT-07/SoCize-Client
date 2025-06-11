package com.socize.utilities.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultObjectMapperProvider implements ObjectMapperProvider {
    private final ObjectMapper objectMapper;

    private DefaultObjectMapperProvider() {
        objectMapper = new ObjectMapper();
    }

    private static class SingletonInstanceHolder {
        private static final DefaultObjectMapperProvider INSTANCE = new DefaultObjectMapperProvider();
    }

    public static DefaultObjectMapperProvider getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
