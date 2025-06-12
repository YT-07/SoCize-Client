package com.socize.pages.fileserver.user;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.api.getdownloadablefiles.dto.GetDownloadableFilesRequest;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.user.dto.DownloadableFile;
import com.socize.pages.fileserver.user.dto.GetDownloadableFilesApiResult;
import com.socize.pages.fileserver.user.model.UserModel;
import com.socize.utilities.textstyler.TextStyler;

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
    private final TextStyler textStyler;

    public UserController(SessionManager sessionManager, UserModel userModel, TextStyler textStyler) {
        this.sessionManager = sessionManager;
        this.userModel = userModel;
        this.textStyler = textStyler;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutButton.setOnAction(e -> logout());
        downloadFileListView.setItems(userModel.getDownloadableFileList());
    }

    /**
     * Helper function to orchestrate the process of logging out.
     */
    private void logout() {
        LogoutRequest logoutRequest = new LogoutRequest(sessionManager.getSessionId());
        userModel.logout(logoutRequest);
    }

    /**
     * Helper function to orchestrate the process of reloading the downloadable file list.
     */
    private void reloadDownloadableFiles() {
        GetDownloadableFilesRequest apiRequest = new GetDownloadableFilesRequest(sessionManager.getSessionId());
        GetDownloadableFilesApiResult apiResult = userModel.getDownloadableFiles(apiRequest);

        if(!apiResult.success()) {
            textStyler.showErrorMessage(downloadFileFeedbackMessage, apiResult.errorMessage());
            return;
        }

        // Only clear items if api request is successful, as it would be the latest update
        downloadFileListView.getItems().clear();

        for(DownloadableFile downloadableFile : apiResult.files()) {
            downloadFileListView.getItems().add(downloadableFile.filename());
        }
    }

    @Override
    public void onEnter() {
        downloadFileListView.getItems().clear();

        downloadFileFeedbackMessage.setText(null);
        selectFileToUploadPath.setText(null);
        uploadFileFeedbackArea.setText(null);
        filenameToSave.setText(null);

        usernameDisplayField.setText(sessionManager.getUsername());
        reloadDownloadableFiles();
    }
    
}
