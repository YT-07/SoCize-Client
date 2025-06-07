package com.socize.api.spi;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.socize.dto.SignUpRequest;

public interface SignUpApi {

    CloseableHttpResponse signup(SignUpRequest signUpRequest);
}