package com.socize.pages.fileserver.admin.usermanagement.model;

import com.socize.api.deleteaccount.dto.DeleteAccountRequest;
import com.socize.api.getaccountdetails.dto.GetAccountDetailsRequest;
import com.socize.api.getuseraccounts.dto.GetUserAccountsRequest;
import com.socize.pages.fileserver.admin.usermanagement.dto.DeleteAccountResult;
import com.socize.pages.fileserver.admin.usermanagement.dto.GetAccountDetailsResult;
import com.socize.pages.fileserver.admin.usermanagement.dto.GetUserAccountsResult;

public interface UserManagementModel {

    /**
     * Gets all user accounts
     * 
     * @param request the user account retrieval request
     * @return the result of the accounts information retrieval
     */
    GetUserAccountsResult getUserAccounts(GetUserAccountsRequest request);

    /**
     * Gets the account details of a specified account
     * 
     * @param request the account details retrieval request
     * @return the result of the account details retrieval
     */
    GetAccountDetailsResult getAccountDetails(GetAccountDetailsRequest request);

    /**
     * Request for a user's account to be deleted
     * 
     * @param request the account deletion request
     * @return the account deletetion result
     */
    DeleteAccountResult deleteAccount(DeleteAccountRequest request);
}