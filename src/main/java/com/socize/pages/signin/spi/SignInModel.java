package com.socize.pages.signin.spi;

import com.socize.dto.SignInRequest;
import com.socize.pages.signin.dto.SignInResult;

public interface SignInModel {

    /**
     * Attempts to sign in the user.
     * 
     * @param signInRequest the sign in request
     * @return the result of the sign in attempt
     */
    SignInResult signin(SignInRequest signInRequest);
}