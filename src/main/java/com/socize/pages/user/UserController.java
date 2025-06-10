package com.socize.pages.user;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.pages.TransitionablePage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class UserController implements Initializable, TransitionablePage {

    @FXML
    private Button deleteFileButton;

    @FXML
    private Button downloadFileButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button selectFileToUploadButton;

    @FXML
    private Button uploadFileButton;

    @FXML
    private Label downloadFileFeedbackMessage;

    @FXML
    private Label selectFileToUploadPath;

    @FXML
    private Label uploadFileFeedbackArea;

    @FXML
    private Label usernameDisplayField;

    @FXML
    private ListView<String> downloadFileListView;

    @FXML
    private TextField filenameToSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @Override
    public void onEnter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnter'");
    }
    
}
