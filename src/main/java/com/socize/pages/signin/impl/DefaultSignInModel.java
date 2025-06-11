package com.socize.pages.signin.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.signin.dto.SignInRequest;
import com.socize.api.signin.spi.SignInApi;
import com.socize.pages.signin.dto.SignInResult;
import com.socize.pages.signin.spi.SignInModel;

public class DefaultSignInModel implements SignInModel {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSignInModel.class);

    private final SignInApi signInApi;
    private final ObjectMapper objectMapper;

    public DefaultSignInModel(SignInApi signInApi, ObjectMapper objectMapper) {
        this.signInApi = signInApi;
        this.objectMapper = objectMapper;
    }

    @Override
    public SignInResult signin(SignInRequest signInRequest) {
        
        try (CloseableHttpResponse response = signInApi.signin(signInRequest)) {

            String jsonResponse = EntityUtils.toString(response.getEntity());
            SignInResult result = objectMapper.readValue(jsonResponse, SignInResult.class);

            return result;

        } catch (Exception e) {
            logger.error("Exception occured while signing in.", e);
            return getDefaultSignInResult();
        }
    }
    
    private static SignInResult getDefaultSignInResult() {
        SignInResult signInResult = new SignInResult(false, null, null, "Something went wrong.", null);

        return signInResult;
    }
}
