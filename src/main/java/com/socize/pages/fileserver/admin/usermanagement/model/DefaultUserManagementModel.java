package com.socize.pages.fileserver.admin.usermanagement.model;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.deleteaccount.DeleteAccountApi;
import com.socize.api.deleteaccount.dto.DeleteAccountRequest;
import com.socize.api.getaccountdetails.GetAccountDetailsApi;
import com.socize.api.getaccountdetails.dto.GetAccountDetailsRequest;
import com.socize.api.getuseraccounts.GetUserAccountsApi;
import com.socize.api.getuseraccounts.dto.GetUserAccountsRequest;
import com.socize.pages.fileserver.admin.usermanagement.dto.DeleteAccountResult;
import com.socize.pages.fileserver.admin.usermanagement.dto.GetAccountDetailsResult;
import com.socize.pages.fileserver.admin.usermanagement.dto.GetUserAccountsResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DefaultUserManagementModel implements UserManagementModel {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserManagementModel.class);

    private final ObjectMapper objectMapper;

    private final GetUserAccountsApi getUserAccountsApi;
    private final GetAccountDetailsApi getAccountDetailsApi;
    private final DeleteAccountApi deleteAccountApi;
    private final ObservableList<String> observableList;

    public DefaultUserManagementModel
    (
        GetUserAccountsApi getUserAccountsApi, 
        ObjectMapper objectMapper, 
        GetAccountDetailsApi getAccountDetailsApi, 
        DeleteAccountApi deleteAccountApi
    ) 
    {
        this.objectMapper = objectMapper;
        this.getUserAccountsApi = getUserAccountsApi;
        this.getAccountDetailsApi = getAccountDetailsApi;
        this.deleteAccountApi = deleteAccountApi;
        this.observableList = FXCollections.observableArrayList();
    }

    @Override
    public GetUserAccountsResult getUserAccounts(GetUserAccountsRequest request) {
        
        try(CloseableHttpResponse response = getUserAccountsApi.getUserAccounts(request)) {

            HttpEntity entity = response.getEntity();

            try {

                String jsonResponse = EntityUtils.toString(entity);
                GetUserAccountsResult result = objectMapper.readValue(jsonResponse, GetUserAccountsResult.class);

                return result;

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }

            }

        } catch (Exception e) {
            logger.error("Exception occured when retrieving user accounts.", e);
            return getDefaultUserAccountsResult();
        }
    }
    
    @Override
    public GetAccountDetailsResult getAccountDetails(GetAccountDetailsRequest request) {
        
        try(CloseableHttpResponse response = getAccountDetailsApi.getAccountDetails(request)) {

            HttpEntity entity = response.getEntity();

            try {

                String jsonResponse = EntityUtils.toString(entity);
                GetAccountDetailsResult result = objectMapper.readValue(jsonResponse, GetAccountDetailsResult.class);

                return result;

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }

            }

        } catch (Exception e) {
            logger.error("Exception occured when retrieving account details for user '{}'", request.accountUsername(), e);
            return getDefaultAccountDetailsResult();
        }
    }

    @Override
    public DeleteAccountResult deleteAccount(DeleteAccountRequest request) {
        
        try(CloseableHttpResponse response = deleteAccountApi.deleteAccount(request)) {

            HttpEntity entity = response.getEntity();

            try {

                String jsonResponse = EntityUtils.toString(entity);
                DeleteAccountResult result = objectMapper.readValue(jsonResponse, DeleteAccountResult.class);

                return result;

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }
                
            }

        } catch (Exception e) {
            logger.error("Exception occured when attempting to delete user {}'s account.", request.accountUsername(), e);
            return getDefaultDeleteAccountResult();
        }

    }

    @Override
    public ObservableList<String> getUserAccountList() {
        return observableList;
    }

    private static GetUserAccountsResult getDefaultUserAccountsResult() {
        return new GetUserAccountsResult(false, "Something went wrong, failed to retrieve user accounts.", null);
    }

    private static GetAccountDetailsResult getDefaultAccountDetailsResult() {
        return new GetAccountDetailsResult(false, "Something went wrong, failed to retrieve account details.", null);
    }

    private static DeleteAccountResult getDefaultDeleteAccountResult() {
        return new DeleteAccountResult(false, "Something went wrong, failed to delete user account.");
    }

}
