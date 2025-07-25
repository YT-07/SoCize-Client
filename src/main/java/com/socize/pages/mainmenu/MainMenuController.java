package com.socize.pages.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.app.sceneprovider.SceneProvider;
import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;
import com.socize.pages.mainmenu.shared.mainmenupagestate.MainMenuPageState;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainMenuController extends PageController implements Initializable {

    @FXML
    private Button encryptionButton;

    @FXML
    private Button decryptionButton;

    @FXML 
    private Button fileServerButton;

    @FXML
    private BorderPane borderPane;

    private final MainMenuPageState pageState;
    private final FileServerPageManager fileServerPageManager;
    private final SceneProvider provider;

    public MainMenuController(MainMenuPageState pageState, SceneProvider provider, FileServerPageManager fileServerPageManager) {
        this.pageState = pageState;
        this.fileServerPageManager = fileServerPageManager;
        this.provider = provider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        pageState.subscribe(scene -> {
            SceneResult<PageController> sceneResult = provider.getScene(scene);
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
            pageState.setPage(fileServerPageManager.getStatus().getFileServerPage());
        });
    }

    @Override
    public void onEnter() {
        
    }

}
