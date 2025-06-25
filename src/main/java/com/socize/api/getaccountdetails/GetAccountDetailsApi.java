package com.socize.api.getaccountdetails;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.getaccountdetails.dto.GetAccountDetailsRequest;

public interface GetAccountDetailsApi {

    /**
     * Attempts to make an api call to get the account details of a specified user.
     * 
     * @param request the api request
     * @return the api response
     * @throws Exception if any exception occurs
     */
    CloseableHttpResponse getAccountDetails(GetAccountDetailsRequest request) throws Exception;
}