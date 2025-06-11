package com.socize.pages.fileserver.homepage;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.fileserverpage.FileServerPageManager;
import com.socize.pages.fileserver.shared.fileserverpage.fileserverpagestatus.DefaultFileServerPageStatus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeController extends PageController implements Initializable {

    @FXML
    private Button signUpButton;

    @FXML
    private Button signInButton;

    private final FileServerPageManager fileServerPageManager;

    public HomeController(FileServerPageManager fileServerPageManager) {
        this.fileServerPageManager = fileServerPageManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signInButton.setOnAction(e -> {
            fileServerPageManager.setStatus(DefaultFileServerPageStatus.SIGNING_IN);
        });

        signUpButton.setOnAction(e -> {
            fileServerPageManager.setStatus(DefaultFileServerPageStatus.SIGNING_UP);
        });
    }

    @Override
    public void onEnter() {
        
    }
    
}
