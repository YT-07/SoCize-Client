package com.socize.pages.fileserver.signup.dto;

public record SignUpValidationError
(
    String username,
    String password,
    String email,
    String phoneNumber
) 
{}