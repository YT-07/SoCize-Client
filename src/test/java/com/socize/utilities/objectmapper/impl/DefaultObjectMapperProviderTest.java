package com.socize.utilities.objectmapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultObjectMapperProviderTest {
    
    @Test
    void shouldReturnSameInstance_WhenGettingInstanceMultipleTimes() {
        DefaultObjectMapperProvider objectMapperProvider1 = DefaultObjectMapperProvider.getInstance();
        DefaultObjectMapperProvider objectMapperProvider2 = DefaultObjectMapperProvider.getInstance();

        assertEquals(objectMapperProvider1, objectMapperProvider2);
    }

    @Test
    void shouldReturnSameObjectMapper_WhenGettingObjectMapperMultipleTimes() {
        DefaultObjectMapperProvider objectMapperProvider = DefaultObjectMapperProvider.getInstance();

        ObjectMapper objectMapper1 = objectMapperProvider.getObjectMapper();
        ObjectMapper objectMapper2 = objectMapperProvider.getObjectMapper();

        assertEquals(objectMapper1, objectMapper2);
    }
}
