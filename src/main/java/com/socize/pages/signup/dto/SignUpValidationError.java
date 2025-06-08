package com.socize.pages.signup.dto;

public record SignUpValidationError
(
    String username,
    String password,
    String email,
    String phoneNumber
) 
{}