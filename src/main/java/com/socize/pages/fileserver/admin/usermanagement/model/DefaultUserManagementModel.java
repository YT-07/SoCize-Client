package com.socize.pages.fileserver.admin.usermanagement.model;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.getuseraccounts.GetUserAccountsApi;
import com.socize.api.getuseraccounts.dto.GetUserAccountsRequest;
import com.socize.pages.fileserver.admin.usermanagement.dto.GetUserAccountsResult;

public class DefaultUserManagementModel implements UserManagementModel {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserManagementModel.class);

    private final ObjectMapper objectMapper;

    private final GetUserAccountsApi getUserAccountsApi;

    public DefaultUserManagementModel(GetUserAccountsApi getUserAccountsApi, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.getUserAccountsApi = getUserAccountsApi;
    }

    @Override
    public GetUserAccountsResult getUserAccounts(GetUserAccountsRequest request) {
        
        try(CloseableHttpResponse response = getUserAccountsApi.getUserAccounts(request)) {

            String jsonResponse = EntityUtils.toString(response.getEntity());
            GetUserAccountsResult result = objectMapper.readValue(jsonResponse, GetUserAccountsResult.class);

            return result;

        } catch (Exception e) {
            logger.error("Exception occured when retrieving user accounts.", e);
            return getDefaultUserAccountsResult();
        }
    }
    
    private static GetUserAccountsResult getDefaultUserAccountsResult() {
        return new GetUserAccountsResult(false, "Failed to retrieve user accounts.", null);
    }
}
