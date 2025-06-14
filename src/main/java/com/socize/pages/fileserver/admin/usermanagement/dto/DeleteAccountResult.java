package com.socize.pages.fileserver.admin.usermanagement.dto;

public record DeleteAccountResult
(
    boolean success,
    String errorMessage
) 
{}