package com.socize.pages.fileserver.admin.serverhealthcheck.dto;

public record ServerStatus
(
    String databaseStatus,
    String cpuUsage,
    String memoryUsage,
    String diskSpaceAvailable
) 
{}