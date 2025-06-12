package com.socize.api.deletefile.dto;

public record DeleteFileRequest
(
    String sessionId,
    String filename
) 
{}
