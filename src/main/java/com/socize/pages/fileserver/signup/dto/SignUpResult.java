package com.socize.pages.fileserver.signup.dto;

public record SignUpResult
(
    boolean success,
    String errorMessage,
    SignUpValidationError validationError
) 
{}