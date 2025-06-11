package com.socize.utilities.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ObjectMapperProvider {

    /**
     * Gets a configured and ready to use object mapper.
     * 
     * @return the object mapper
     */
    ObjectMapper getObjectMapper();
}