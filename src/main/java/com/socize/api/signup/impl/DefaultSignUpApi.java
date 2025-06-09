package com.socize.api.signup.impl;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.signup.spi.SignUpApi;
import com.socize.dto.SignUpRequest;

public class DefaultSignUpApi implements SignUpApi {

    @Override
    public CloseableHttpResponse signup(SignUpRequest signUpRequest) {
        // TODO

        return null;
    }
    
}
