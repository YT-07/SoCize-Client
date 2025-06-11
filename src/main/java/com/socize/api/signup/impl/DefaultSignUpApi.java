package com.socize.api.signup.impl;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.signup.dto.SignUpRequest;
import com.socize.api.signup.spi.SignUpApi;

public class DefaultSignUpApi implements SignUpApi {

    @Override
    public CloseableHttpResponse signup(SignUpRequest signUpRequest) {
        // TODO

        return null;
    }
    
}
