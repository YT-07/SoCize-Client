package com.socize.pages.fileserver.admin.serverhealthcheck.model;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.serverhealthcheck.ServerHealthcheckApi;
import com.socize.api.serverhealthcheck.dto.ServerHealthcheckRequest;
import com.socize.pages.fileserver.admin.serverhealthcheck.dto.ServerHealthcheckResult;

public class DefaultServerHealthcheckModel implements ServerHealthcheckModel {
    private static final Logger logger = LoggerFactory.getLogger(DefaultServerHealthcheckModel.class);

    private final ObjectMapper objectMapper;
    private final ServerHealthcheckApi serverHealthcheckApi;

    public DefaultServerHealthcheckModel(ObjectMapper objectMapper, ServerHealthcheckApi serverHealthcheckApi) {
        this.objectMapper = objectMapper;
        this.serverHealthcheckApi = serverHealthcheckApi;
    }

    @Override
    public ServerHealthcheckResult getServerStatus(ServerHealthcheckRequest request) {
        
        try(CloseableHttpResponse response = serverHealthcheckApi.getServerStatus(request)) {

            HttpEntity entity = response.getEntity();

            try {

                String jsonResponse = EntityUtils.toString(entity);
                ServerHealthcheckResult result = objectMapper.readValue(jsonResponse, ServerHealthcheckResult.class);

                return result;

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }
                
            }

        } catch(Exception e) {
            logger.error("Exception occured when getting server status.", e);
            return getDefaultServerHealthcheckResult();
        }
    }
    
    private static ServerHealthcheckResult getDefaultServerHealthcheckResult() {
        return new ServerHealthcheckResult(false, "Unable to fetch server status.", null);
    }
}
