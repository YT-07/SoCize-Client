package com.socize.pages.fileserver.admin.usermanagement.dto;

public record GetAccountDetailsResult
(
    boolean success,
    String errorMessage,
    AccountDetails details
) 
{}