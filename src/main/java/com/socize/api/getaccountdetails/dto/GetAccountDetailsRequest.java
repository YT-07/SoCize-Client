package com.socize.api.getaccountdetails.dto;

public record GetAccountDetailsRequest
(
    String sessionId,
    String accountUsername
) 
{}