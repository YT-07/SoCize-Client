package com.socize.api.impl;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.spi.SignUpApi;
import com.socize.dto.SignUpRequest;

public class DefaultSignUpApi implements SignUpApi {

    @Override
    public CloseableHttpResponse signup(SignUpRequest signUpRequest) {
        // TODO

        return null;
    }
    
}
