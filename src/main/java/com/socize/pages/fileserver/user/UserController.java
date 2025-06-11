package com.socize.pages.fileserver.user;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.api.logout.dto.LogoutRequest;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.user.model.UserModel;

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

    private final SessionManager sessionManager;
    private final UserModel userModel;

    public UserController(SessionManager sessionManager, UserModel userModel) {
        this.sessionManager = sessionManager;
        this.userModel = userModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutButton.setOnAction(e -> logout());
    }

    /**
     * Helper function to orchestrate the process of logging out.
     */
    private void logout() {
        LogoutRequest logoutRequest = new LogoutRequest(sessionManager.getSessionId());
        userModel.logout(logoutRequest);
    }

    @Override
    public void onEnter() {
        downloadFileListView.getItems().clear();

        downloadFileFeedbackMessage.setText(null);
        selectFileToUploadPath.setText(null);
        uploadFileFeedbackArea.setText(null);
        filenameToSave.setText(null);

        usernameDisplayField.setText(sessionManager.getUsername());

        // TODO: Get file to download from API and display in listview
    }
    
}
