package com.socize.pages.encryption;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EncryptionController implements Initializable {

    @FXML
    private Button selectFileToEncryptButton;

    @FXML
    private Button selectFolderToSaveButton;

    @FXML
    private Button encryptButton;

    @FXML
    private Label fileToEncryptPath;

    @FXML
    private Label folderToSavePath;

    @FXML
    private Label encryptFeedbackField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
