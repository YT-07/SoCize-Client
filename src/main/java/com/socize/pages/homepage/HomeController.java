package com.socize.pages.homepage;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.app.sceneloader.AppScene;
import com.socize.shared.spi.MainMenuPageState;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeController implements Initializable {

    @FXML
    private Button signUpButton;

    @FXML
    private Button signInButton;

    private final MainMenuPageState mainMenuPageState;

    public HomeController(MainMenuPageState mainMenuPageState) {
        this.mainMenuPageState = mainMenuPageState;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signInButton.setOnAction(e -> {
            mainMenuPageState.setPage(AppScene.SIGN_IN_PAGE);
        });

        signUpButton.setOnAction(e -> {
            mainMenuPageState.setPage(AppScene.SIGN_UP_PAGE);
        });
    }
    
}
