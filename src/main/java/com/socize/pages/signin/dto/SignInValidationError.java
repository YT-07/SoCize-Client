package com.socize.pages.signin.dto;

public record SignInValidationError
(
    String username,
    String password
) 
{}