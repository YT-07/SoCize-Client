package com.socize.pages.fileserver.signup.model;

import com.socize.api.signup.dto.SignUpRequest;
import com.socize.pages.fileserver.signup.dto.SignUpResult;

public interface SignUpModel {

    /**
     * Attempts to sign up the user.
     * 
     * @param signUpRequest the sign up request that contains all necessary data for signing up
     * @return the result of the sign up attempt
     */
    SignUpResult signup(SignUpRequest signUpRequest);
}