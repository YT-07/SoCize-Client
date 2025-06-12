package com.socize.api.downloadfile.dto;

public record DownloadFileRequest
(
    String sessionId,
    String filename
) 
{}