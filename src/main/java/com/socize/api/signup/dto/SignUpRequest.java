package com.socize.api.signup.dto;

public record SignUpRequest
(
    String username,
    String password,
    String email,
    String phoneNumber
) 
{}