package com.socize.pages.fileserver.signin.model;

import com.socize.api.signin.dto.SignInRequest;
import com.socize.pages.fileserver.signin.dto.SignInResult;

public interface SignInModel {

    /**
     * Attempts to sign in the user.
     * 
     * @param signInRequest the sign in request
     * @return the result of the sign in attempt
     */
    SignInResult signin(SignInRequest signInRequest);
}