package com.socize.pages.fileserver.signin.dto;

public record SignInValidationError
(
    String username,
    String password
) 
{}