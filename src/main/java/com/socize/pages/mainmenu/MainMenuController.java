package com.socize.pages.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.app.sceneprovider.SceneProvider;
import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.pages.TransitionablePage;
import com.socize.shared.mainmenupagestate.MainMenuPageState;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainMenuController implements Initializable, TransitionablePage {

    @FXML
    private Button encryptionButton;

    @FXML
    private Button decryptionButton;

    @FXML 
    private Button fileServerButton;

    @FXML
    private BorderPane borderPane;

    private final MainMenuPageState pageState;
    private final SceneProvider provider;

    public MainMenuController(MainMenuPageState pageState, SceneProvider provider) {
        this.pageState = pageState;
        this.provider = provider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        pageState.subscribe(scene -> {
            SceneResult<TransitionablePage> sceneResult = provider.getScene(scene);
            sceneResult.controller().onEnter();
            borderPane.setRight(sceneResult.parent());
        });

        // Default page to display
        pageState.setPage(DefaultAppScenes.ENCRYPTION_PAGE);

        encryptionButton.setOnAction(e -> {
            pageState.setPage(DefaultAppScenes.ENCRYPTION_PAGE);
        });

        decryptionButton.setOnAction(e -> {
            pageState.setPage(DefaultAppScenes.DECRYPTION_PAGE);
        });

        fileServerButton.setOnAction(e -> {
            pageState.setPage(DefaultAppScenes.HOME_PAGE);
        });
    }

    @Override
    public void onEnter() {
        
    }

}
