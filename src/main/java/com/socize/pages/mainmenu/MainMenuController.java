package com.socize.pages.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.spi.SceneLoader;
import com.socize.shared.spi.MainMenuPageState;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainMenuController implements Initializable {

    @FXML
    private Button encryptionButton;

    @FXML
    private Button decryptionButton;

    @FXML 
    private Button fileServerButton;

    @FXML
    private BorderPane borderPane;

    private final MainMenuPageState pageState;
    private final SceneLoader loader;

    public MainMenuController(MainMenuPageState pageState, SceneLoader loader) {
        this.pageState = pageState;
        this.loader = loader;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        pageState.subscribe(scene -> {
            Parent newParent = loader.getScene(scene);
            borderPane.setRight(newParent);
        });

        // TODO: Set page to encryption page by default

        fileServerButton.setOnAction(e -> {
            pageState.setPage(AppScene.HOME_PAGE);
        });
    }


}
