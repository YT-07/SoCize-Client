package com.socize.api.signin.spi;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.dto.SignInRequest;

public interface SignInApi {

    /**
     * Attempts to make an api call to sign in the user.
     * 
     * @param signInRequest the sign in request that contains all required data to sign in
     * @return the api response
     */
    CloseableHttpResponse signin(SignInRequest signInRequest);
}