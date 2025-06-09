package com.socize.pages.signin;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.app.sceneloader.AppScene;
import com.socize.dto.SignInRequest;
import com.socize.pages.signin.spi.SignInModel;
import com.socize.shared.mainmenupagestate.spi.MainMenuPageState;
import com.socize.utilities.textstyler.spi.TextStyler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignInController implements Initializable {

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

    private final MainMenuPageState mainMenuPageState;
    private final TextStyler textStyler;
    private final SignInModel signInModel;

    public SignInController(MainMenuPageState mainMenuPageState, TextStyler textStyler, SignInModel signInModel) {
        this.mainMenuPageState = mainMenuPageState;
        this.textStyler = textStyler;
        this.signInModel = signInModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(e -> {
            mainMenuPageState.setPage(AppScene.HOME_PAGE);
        });

        signUpButton.setOnAction(e -> {
            mainMenuPageState.setPage(AppScene.SIGN_UP_PAGE);
        });

        signInButton.setOnAction(e -> signin());
    }
    
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


    }

    /**
     * Helper function to clear all text fields to prepare for new feedback messages.
     */
    private void clearTextFields() {
        usernameFeedbackField.setText(null);
        passwordFeedbackField.setText(null);
        signinFeedbackField.setText(null);
    }
}
