package com.socize.pages.fileserver.admin.usermanagement.model;

import com.socize.api.getuseraccounts.dto.GetUserAccountsRequest;
import com.socize.pages.fileserver.admin.usermanagement.dto.GetUserAccountsResult;

public interface UserManagementModel {

    /**
     * Gets all user accounts
     * 
     * @param request the user account retrieval request
     * @return the result of the accounts information retrieval
     */
    GetUserAccountsResult getUserAccounts(GetUserAccountsRequest request);
}