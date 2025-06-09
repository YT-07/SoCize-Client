package com.socize.pages.signin.dto;

public record SignInResult
(
    boolean success,
    String sessionId,
    String role,
    String errorMessage,
    SignInValidationError validationError
) 
{}