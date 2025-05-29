package com.socize.pages.signup;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SignUpController implements Initializable {

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
    private TextField addressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text usernameErrorText;

    @FXML
    private Text passwordErrorText;

    @FXML
    private Text emailErrorText;

    @FXML
    private Text addressErrorText;

    @FXML
    private Text signUpErrorText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
