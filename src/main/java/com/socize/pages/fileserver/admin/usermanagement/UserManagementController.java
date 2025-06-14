package com.socize.pages.fileserver.admin.usermanagement;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.api.deleteaccount.dto.DeleteAccountRequest;
import com.socize.api.getaccountdetails.dto.GetAccountDetailsRequest;
import com.socize.api.getuseraccounts.dto.GetUserAccountsRequest;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.admin.usermanagement.dto.AccountDetails;
import com.socize.pages.fileserver.admin.usermanagement.dto.DeleteAccountResult;
import com.socize.pages.fileserver.admin.usermanagement.dto.GetAccountDetailsResult;
import com.socize.pages.fileserver.admin.usermanagement.dto.GetUserAccountsResult;
import com.socize.pages.fileserver.admin.usermanagement.dto.UserAcccount;
import com.socize.pages.fileserver.admin.usermanagement.model.UserManagementModel;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.utilities.logoutservice.LogoutService;
import com.socize.utilities.textstyler.TextStyler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class UserManagementController extends PageController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button viewUserDetailButton;

    @FXML
    private Label accountEmailField;

    @FXML
    private Label accountPhoneNumberField;

    @FXML
    private Label accountUsernameField;

    @FXML
    private Label feedbackMessageField;

    @FXML
    private Label usernameDisplayField;

    @FXML
    private ListView<String> userListView;

    private final SessionManager sessionManager;
    private final TextStyler textStyler;
    private final UserManagementModel model;
    private final LogoutService logoutService;

    public UserManagementController(SessionManager sessionManager, UserManagementModel model, TextStyler textStyler, LogoutService logoutService) {
        this.sessionManager = sessionManager;
        this.textStyler = textStyler;
        this.model = model;
        this.logoutService = logoutService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userListView.setItems(model.getUserAccountList());

        logoutButton.setOnAction(e -> logout());

        viewUserDetailButton.setOnAction(e -> viewAccountDetails());

        deleteUserButton.setOnAction(e -> deleteAccount());
    }

    /**
     * Helper function to orchestrate the functionality to reload the user accounts list.
     */
    private void reloadUserList() {
        userListView.getItems().clear();

        GetUserAccountsRequest request = new GetUserAccountsRequest(sessionManager.getSessionId());
        GetUserAccountsResult result = model.getUserAccounts(request);

        if(!result.success()) {
            textStyler.showErrorMessage(feedbackMessageField, result.errorMessage());
            return;
        }

        if(result.accounts() == null) {
            logger.warn("Results for retrieving user accounts indicates that account retrieval is successful, but accounts received is null, should be empty array. Results received: ", result.toString());
            textStyler.showErrorMessage(feedbackMessageField, "Successfully retrieved user accounts, but there's no accounts to display.");
            return;
        }

        for(UserAcccount acccount : result.accounts()) {
            userListView.getItems().add(acccount.username());
        }
    }

    /**
     * Helper function to orchestrate the process of retrieving and displaying the specified account's details.
     */
    private void viewAccountDetails() {
        resetAccountDetails();

        String selectedUser = userListView.getSelectionModel().getSelectedItem();

        if(selectedUser == null) {
            textStyler.showErrorMessage(feedbackMessageField, "Please select a user.");
            return;
        }

        GetAccountDetailsRequest request = new GetAccountDetailsRequest(sessionManager.getSessionId(), selectedUser);
        GetAccountDetailsResult result = model.getAccountDetails(request);

        if(!result.success()) {
            textStyler.showErrorMessage(feedbackMessageField, result.errorMessage());
            return;
        }

        AccountDetails accountDetails = result.details();

        if(accountDetails == null) {
            logger.warn("Result when retrieving account details for user '{}' indicates that its successful, but account details received is null.");
            textStyler.showErrorMessage(feedbackMessageField, "Account details successfully retrieved, but there's no details to display.");
            return;
        }

        accountUsernameField.setText(accountDetails.username());
        accountEmailField.setText(accountDetails.email());
        accountPhoneNumberField.setText(accountDetails.phoneNumber());

        textStyler.showSuccessMessage(feedbackMessageField, "Account details successfully retrieved.");
    }

    private void logout() {
        LogoutRequest request = new LogoutRequest(sessionManager.getSessionId());
        logoutService.logout(request);
    }

    /**
     * Helper function to orchestrate the process of requesting a user's account to be deleted.
     */
    private void deleteAccount() {
        resetAccountDetails();

        String selectedUser = userListView.getSelectionModel().getSelectedItem();

        if(selectedUser == null) {
            textStyler.showErrorMessage(feedbackMessageField, "Please select a user.");
            return;
        }

        DeleteAccountRequest request = new DeleteAccountRequest(sessionManager.getSessionId(), selectedUser);
        DeleteAccountResult result = model.deleteAccount(request);

        if(result.success()) {
            textStyler.showSuccessMessage(feedbackMessageField, "User account successfully deleted.");
            userListView.getItems().remove(selectedUser);

        } else {
            textStyler.showErrorMessage(feedbackMessageField, result.errorMessage());

        }
    }

    /**
     * Helper function to reset the account details to its default value
     */
    private void resetAccountDetails() {
        accountUsernameField.setText("-");
        accountEmailField.setText("-");
        accountPhoneNumberField.setText("-");
    }

    @Override
    public void onEnter() {
        reloadUserList();
        resetAccountDetails();

        usernameDisplayField.setText(sessionManager.getUsername());
    }
    
}
