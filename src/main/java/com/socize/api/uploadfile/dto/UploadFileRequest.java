package com.socize.api.uploadfile.dto;

import java.io.File;

public record UploadFileRequest
(
    String sessionId,
    File fileToUpload
) 
{}