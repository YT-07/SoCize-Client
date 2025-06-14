package com.socize.pages.fileserver.admin.usermanagement.dto;

import java.util.Set;

public record GetUserAccountsResult
(
    boolean success,
    String errorMessage,
    Set<UserAcccount> accounts
) 
{}