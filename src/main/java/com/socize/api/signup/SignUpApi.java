package com.socize.api.signup;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.signup.dto.SignUpRequest;

public interface SignUpApi {

    /**
     * Attempts to make an api call to sign up the user.
     * 
     * @param signUpRequest the sign up request that contains all necessary sign up data
     * @return the api response
     * @throws Exception if any exception occur
     */
    CloseableHttpResponse signup(SignUpRequest signUpRequest) throws Exception;
}