package com.socize.pages.signup.dto;

public record SignUpResult
(
    boolean success,
    String errorMessage,
    SignUpValidationError validationError
) 
{}