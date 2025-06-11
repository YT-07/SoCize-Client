package com.socize.pages.homepage;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;
import com.socize.pages.PageController;
import com.socize.shared.mainmenupagestate.MainMenuPageState;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeController extends PageController implements Initializable {

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
            mainMenuPageState.setPage(DefaultAppScenes.SIGN_IN_PAGE);
        });

        signUpButton.setOnAction(e -> {
            mainMenuPageState.setPage(DefaultAppScenes.SIGN_UP_PAGE);
        });
    }

    @Override
    public void onEnter() {
        
    }
    
}
