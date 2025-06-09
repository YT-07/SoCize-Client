package com.socize.pages.signin;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.api.signin.spi.SignInApi;
import com.socize.app.sceneloader.AppScene;
import com.socize.shared.mainmenupagestate.spi.MainMenuPageState;
import com.socize.utilities.textstyler.spi.TextStyler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private Text usernameErrorText;

    @FXML
    private Text passwordErrorText;

    @FXML
    private Text signInErrorText;

    private final MainMenuPageState mainMenuPageState;
    private final SignInApi signInApi;
    private final TextStyler textStyler;

    public SignInController(MainMenuPageState mainMenuPageState, SignInApi signInApi, TextStyler textStyler) {
        this.mainMenuPageState = mainMenuPageState;
        this.signInApi = signInApi;
        this.textStyler = textStyler;
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


    }

    private void clearTextFields() {
        usernameErrorText.setText(null);
        passwordErrorText.setText(null);
        signInErrorText.setText(null);
    }
}
