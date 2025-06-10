package com.socize.pages.homepage;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.app.sceneloader.AppScene;
import com.socize.pages.TransitionablePage;
import com.socize.shared.mainmenupagestate.spi.MainMenuPageState;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeController implements Initializable, TransitionablePage {

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

    @Override
    public void onEnter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnter'");
    }

    @Override
    public void onExit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onExit'");
    }
    
}
