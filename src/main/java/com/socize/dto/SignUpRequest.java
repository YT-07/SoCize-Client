package com.socize.dto;

public record SignUpRequest
(
    String username,
    String password,
    String email,
    String phoneNumber
) 
{}