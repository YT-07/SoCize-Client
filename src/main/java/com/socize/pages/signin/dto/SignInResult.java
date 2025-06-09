package com.socize.pages.signin.dto;

public record SignInResult
(
    boolean success,
    String sessionId,
    UserRole role,
    String errorMessage,
    SignInValidationError validationError
) 
{
    private static enum UserRole 
    {
        admin,
        user;
    }
}