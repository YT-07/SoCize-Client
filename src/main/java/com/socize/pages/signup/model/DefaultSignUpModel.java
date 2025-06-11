package com.socize.pages.signup.model;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.signup.SignUpApi;
import com.socize.api.signup.dto.SignUpRequest;
import com.socize.pages.signup.dto.SignUpResult;

public class DefaultSignUpModel implements SignUpModel {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSignUpModel.class);

    private final SignUpApi signUpApi;
    private final ObjectMapper objectMapper;

    public DefaultSignUpModel(SignUpApi signUpApi, ObjectMapper objectMapper) {
        this.signUpApi = signUpApi;
        this.objectMapper = objectMapper;
    }

    @Override
    public SignUpResult signup(SignUpRequest signUpRequest) {
        
        try (CloseableHttpResponse response = signUpApi.signup(signUpRequest)) {
            
            String jsonResponse = EntityUtils.toString(response.getEntity());
            SignUpResult result = objectMapper.readValue(jsonResponse, SignUpResult.class);

            return result;

        } catch (Exception e) {
            logger.error("Exception occured while signing up.", e);
            return getDefaultSignUpResult();
        }

    }
    
    /**
     * Gets the default sign up result if anything went wrong.
     * 
     * @return the default result
     */
    private static SignUpResult getDefaultSignUpResult() {
        SignUpResult result = new SignUpResult(false, "Something went wrong...", null);

        return result;
    }
}
