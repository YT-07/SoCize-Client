package com.socize.pages.fileserver.admin.serverhealthcheck.dto;

public record ServerHealthcheckResult
(
    boolean success,
    String errorMessage,
    ServerStatus status
) 
{}