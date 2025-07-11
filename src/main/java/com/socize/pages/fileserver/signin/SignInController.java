package com.socize.pages.fileserver.signin;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.api.signin.dto.SignInRequest;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.fileserverpagestatus.DefaultFileServerPageStatus;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.signin.dto.SignInResult;
import com.socize.pages.fileserver.signin.dto.SignInResult.UserRole;
import com.socize.pages.fileserver.signin.model.SignInModel;
import com.socize.utilities.textstyler.TextStyler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignInController extends PageController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(SignInController.class); 

    @FXML
    private Button homeButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button signInButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label usernameFeedbackField;

    @FXML
    private Label passwordFeedbackField;

    @FXML
    private Label signinFeedbackField;

    private final FileServerPageManager fileServerPageManager;
    private final TextStyler textStyler;
    private final SignInModel signInModel;
    private final SessionManager sessionManager;

    public SignInController
    (
        FileServerPageManager fileServerPageManager,
        TextStyler textStyler, 
        SignInModel signInModel,
        SessionManager sessionIdManager
    ) 
    {
        this.fileServerPageManager = fileServerPageManager;
        this.textStyler = textStyler;
        this.signInModel = signInModel;
        this.sessionManager = sessionIdManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(e -> {
            fileServerPageManager.setStatus(DefaultFileServerPageStatus.AT_HOME);
        });

        signUpButton.setOnAction(e -> {
            fileServerPageManager.setStatus(DefaultFileServerPageStatus.SIGNING_UP);
        });

        signInButton.setOnAction(e -> signin());
    }
    
    /**
     * Helper function to orchestrate the sign in process.
     */
    private void signin() {
        clearTextFields();

        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.isEmpty()) {
            textStyler.showErrorMessage(usernameFeedbackField, "Please enter your username.");
        }

        if(password.isEmpty()) {
            textStyler.showErrorMessage(passwordFeedbackField, "Please enter your password.");
        }

        if(username.isEmpty() || password.isEmpty()) {
            return;
        }

        SignInRequest signInRequest = new SignInRequest(username, password);
        SignInResult result = signInModel.signin(signInRequest);

        displayFeedbackMessages(result);
        redirectIfSuccess(result, signInRequest.username());
    }

    /**
     * Helper function to clear all text fields to prepare for new feedback messages.
     */
    private void clearTextFields() {
        usernameFeedbackField.setText(null);
        passwordFeedbackField.setText(null);
        signinFeedbackField.setText(null);
    }

    /**
     * Helper function to display feedback messages to users
     * 
     * @param result the sign in result
     */
    private void displayFeedbackMessages(SignInResult result) {

        if(result.success()) {
            textStyler.showSuccessMessage(signinFeedbackField, "Sign in successful.");
            return;
        }

        if(result.errorMessage() != null) {
            textStyler.showErrorMessage(signinFeedbackField, result.errorMessage());
        }

        if(result.validationError() == null) {
            return;
        }

        if(result.validationError().username() != null) {
            textStyler.showErrorMessage(usernameFeedbackField, result.validationError().username());
        }

        if(result.validationError().password() != null) {
            textStyler.showErrorMessage(usernameFeedbackField, result.validationError().password());
        }
    }

    /**
     * Helper function to handle the logic of storing the user session and 
     * redirecting user to their relevant page.
     * 
     * @param result the sign in result
     * @param username the username of the user to store for this session
     */
    private void redirectIfSuccess(SignInResult result, String username) {

        if(!result.success()) {
            return;
        }

        if(result.sessionId() == null) {
            logger.error("Login is successful but session id is null. Sign in data received: {}", result.toString());
            textStyler.showErrorMessage(signinFeedbackField, "Something went wrong, sign in is successful but failed to retrieve your session.");

            return;
        }

        sessionManager.setSession(result.sessionId(), username);

        // So next visit to login page won't store old data
        clearTextFields(); 

        if(result.role() == null) {
            logger.error("User login is successful but user role received is null, unable to redirect user to next page.");
            textStyler.showErrorMessage(signinFeedbackField, "Something went wrong, unable to redirect you to the next page...");

        } else if(result.role() == UserRole.admin) {
            fileServerPageManager.setStatus(DefaultFileServerPageStatus.AT_ADMIN_PAGE);

        } else if(result.role() == UserRole.user) {
            fileServerPageManager.setStatus(DefaultFileServerPageStatus.AT_USER_PAGE);

        } else {
            logger.error("User login is successful but unable to match user role to redirect user. User role received: '{}'.", result.role().name());
            textStyler.showErrorMessage(signinFeedbackField, "Something went wrong, unable to redirect you to the next page...");
        }
    }

    @Override
    public void onEnter() {
        usernameField.clear();
        passwordField.clear();
        usernameFeedbackField.setText(null);
        passwordFeedbackField.setText(null);
        signinFeedbackField.setText(null);
    }

}
