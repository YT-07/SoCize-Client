package com.socize.api.signin.impl;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.api.signin.spi.SignInApi;
import com.socize.dto.SignInRequest;

public class DefaultSignInApi implements SignInApi {

    @Override
    public CloseableHttpResponse signin(SignInRequest signInRequest) {
        // TODO

        return null;
    }
    
}
