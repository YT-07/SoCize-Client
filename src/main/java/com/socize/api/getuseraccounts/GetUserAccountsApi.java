package com.socize.api.getuseraccounts;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.getuseraccounts.dto.GetUserAccountsRequest;

public interface GetUserAccountsApi {

    /**
     * Attempts to make an api call to retrieve all user accounts.
     * 
     * @param request the api request
     * @return the api response
     * @throws Exception if any exception occurs
     */
    CloseableHttpResponse getUserAccounts(GetUserAccountsRequest request) throws Exception;
}
