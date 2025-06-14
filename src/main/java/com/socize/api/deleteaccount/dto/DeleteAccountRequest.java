package com.socize.api.deleteaccount.dto;

public record DeleteAccountRequest
(
    String sessionId,
    String accountUsername
) 
{}