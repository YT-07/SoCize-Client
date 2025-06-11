package com.socize.pages.user;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.pages.PageController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class UserController extends PageController implements Initializable {

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
        downloadFileListView.getItems().clear();

        downloadFileFeedbackMessage.setText(null);
        selectFileToUploadPath.setText(null);
        uploadFileFeedbackArea.setText(null);
        filenameToSave.setText(null);

        // TODO: Get username to display
        // TODO: Get file to download from API and display in listview
    }
    
}
