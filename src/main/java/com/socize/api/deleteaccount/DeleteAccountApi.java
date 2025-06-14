package com.socize.api.deleteaccount;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.deleteaccount.dto.DeleteAccountRequest;

public interface DeleteAccountApi {

    /**
     * Attempts to make an api call to request a user's account to be deleted.
     * 
     * @param request the api request
     * @return the api response
     */
    CloseableHttpResponse deleteAccount(DeleteAccountRequest request);
}
