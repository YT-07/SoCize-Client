package com.socize.pages.fileserver.signin.model;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.signin.SignInApi;
import com.socize.api.signin.dto.SignInRequest;
import com.socize.pages.fileserver.signin.dto.SignInResult;

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

            HttpEntity entity = response.getEntity();

            try {
                
                String jsonResponse = EntityUtils.toString(entity);
                SignInResult result = objectMapper.readValue(jsonResponse, SignInResult.class);

                return result;

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }

            }

        } catch (Exception e) {
            logger.error("Exception occured while signing in.", e);
            return getDefaultSignInResult();
        }
    }
    
    /**
     * Gets the default sign in result if anything went wrong while retrieving the sign in result.
     * 
     * @return the default sign in result
     */
    private static SignInResult getDefaultSignInResult() {
        SignInResult signInResult = new SignInResult(false, null, null, "Something went wrong.", null);

        return signInResult;
    }
}
