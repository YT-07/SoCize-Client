package com.socize.pages.fileserver.signup;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.api.signup.dto.SignUpRequest;
import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.signup.dto.SignUpResult;
import com.socize.pages.fileserver.signup.dto.SignUpValidationError;
import com.socize.pages.fileserver.signup.model.SignUpModel;
import com.socize.pages.mainmenu.shared.mainmenupagestate.MainMenuPageState;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SignUpController extends PageController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text usernameErrorText;

    @FXML
    private Text passwordErrorText;

    @FXML
    private Text emailErrorText;

    @FXML
    private Text phoneNumberErrorText;

    @FXML
    private Text signUpFeedbackText;

    private final MainMenuPageState mainMenuPageState;
    private final SignUpModel signUpModel;

    public SignUpController(MainMenuPageState mainMenuPageState, SignUpModel signUpModel) {
        this.mainMenuPageState = mainMenuPageState;
        this.signUpModel = signUpModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(e -> {
            mainMenuPageState.setPage(DefaultAppScenes.HOME_PAGE);
        });

        signInButton.setOnAction(e -> {
            mainMenuPageState.setPage(DefaultAppScenes.SIGN_IN_PAGE);
        });

        signUpButton.setOnAction(e -> signup());
    }
    
    /**
     * Helper function to orchestrate the sign up process.
     */
    private void signup() {
        clearFeedbackFields();

        SignUpRequest signUpRequest = new SignUpRequest(
            usernameField.getText(), 
            passwordField.getText(), 
            emailField.getText(), 
            phoneNumberField.getText()
        );

        SignUpResult signUpResult = signUpModel.signup(signUpRequest);

        displayFeedbackMessages(signUpResult);
    }

    /**
     * Helper function to clear all feedback area to prepare for new feedback messages.
     */
    private void clearFeedbackFields() {
        usernameErrorText.setText(null);
        passwordErrorText.setText(null);
        emailErrorText.setText(null);
        phoneNumberErrorText.setText(null);
        signUpFeedbackText.setText(null);
    }

    /**
     * Helper function to display feedback messages to users
     * 
     * @param result the sign up result
     */
    private void displayFeedbackMessages(SignUpResult result) {
        if(result.success()) {
            signUpFeedbackText.setText("Successfully signed up!");

            return;
        }

        if(result.errorMessage() != null) {
            signUpFeedbackText.setText(result.errorMessage());
        }

        SignUpValidationError validationError = result.validationError();

        if(validationError == null) {
            return;
        }

        if(validationError.username() != null) {
            usernameErrorText.setText(validationError.username());
        }

        if(validationError.password() != null) {
            passwordErrorText.setText(validationError.password());
        }

        if(validationError.email() != null) {
            emailErrorText.setText(validationError.email());
        }

        if(validationError.phoneNumber() != null) {
            phoneNumberErrorText.setText(validationError.phoneNumber());
        }
    }

    @Override
    public void onEnter() {
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
        phoneNumberField.clear();

        usernameErrorText.setText(null);
        passwordErrorText.setText(null);
        emailErrorText.setText(null);
        phoneNumberErrorText.setText(null);
        signUpFeedbackText.setText(null);
    }

}
