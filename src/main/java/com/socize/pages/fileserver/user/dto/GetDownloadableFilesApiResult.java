package com.socize.pages.fileserver.user.dto;

import java.util.Set;

public record GetDownloadableFilesApiResult
(
    boolean success,
    String errorMessage,
    Set<DownloadableFile> files
) 
{}