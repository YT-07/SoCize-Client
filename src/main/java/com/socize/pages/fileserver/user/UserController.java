package com.socize.pages.fileserver.user;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.socize.api.deletefile.dto.DeleteFileRequest;
import com.socize.api.downloadfile.dto.DownloadFileRequest;
import com.socize.api.getdownloadablefiles.dto.GetDownloadableFilesRequest;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.api.uploadfile.dto.UploadFileRequest;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.user.dto.DeleteFileResult;
import com.socize.pages.fileserver.user.dto.DownloadFileResult;
import com.socize.pages.fileserver.user.dto.DownloadableFile;
import com.socize.pages.fileserver.user.dto.GetDownloadableFilesApiResult;
import com.socize.pages.fileserver.user.dto.UploadFileResult;
import com.socize.pages.fileserver.user.model.UserModel;
import com.socize.utilities.textstyler.TextStyler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

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
    private final FileChooser fileChooser;

    private File fileToUpload;

    public UserController(SessionManager sessionManager, UserModel userModel, TextStyler textStyler) {
        this.sessionManager = sessionManager;
        this.userModel = userModel;
        this.textStyler = textStyler;

        this.fileChooser = new FileChooser();
        fileChooser.setTitle("Select file to save");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutButton.setOnAction(e -> logout());
        downloadFileListView.setItems(userModel.getDownloadableFileList());

        deleteFileButton.setOnAction(e -> deleteFile());

        downloadFileButton.setOnAction(e -> downloadFile());

        selectFileToUploadButton.setOnAction(e -> selectFileToUpload());

        uploadFileButton.setOnAction(e -> uploadFile());
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

    /**
     * Helper function to orchestrate the file deletion process.
     */
    private void deleteFile() {
        String selectedFile = downloadFileListView.getSelectionModel().getSelectedItem();

        if(selectedFile == null) {
            textStyler.showErrorMessage(downloadFileFeedbackMessage, "Please select a file to delete.");
            return;
        }

        DeleteFileRequest request = new DeleteFileRequest(sessionManager.getSessionId(), selectedFile);
        DeleteFileResult result = userModel.deleteFile(request);

        if(result.success()) {
            downloadFileListView.getItems().remove(selectedFile);
            textStyler.showSuccessMessage(downloadFileFeedbackMessage, "File successfully deleted.");

        } else {
            textStyler.showErrorMessage(downloadFileFeedbackMessage, result.errorMessage());

        }
        
    }

    /**
     * Helper function to orchestrate the file download process.
     */
    private void downloadFile() {
        String selectedFile = downloadFileListView.getSelectionModel().getSelectedItem();

        if(selectedFile == null) {
            textStyler.showErrorMessage(downloadFileFeedbackMessage, "Please select a file to download.");
            return;
        }

        Window window = downloadFileButton.getScene().getWindow();
        File pathToSaveFile = fileChooser.showSaveDialog(window);

        if(pathToSaveFile == null) {
            textStyler.showErrorMessage(downloadFileFeedbackMessage, "Please select a path to download the file.");
            return;
        }

        DownloadFileRequest request = new DownloadFileRequest(sessionManager.getSessionId(), selectedFile);
        DownloadFileResult result = userModel.downloadFile(request, pathToSaveFile);

        if(result.success()) {
            textStyler.showSuccessMessage(downloadFileFeedbackMessage, "File successfully downloaded.");

        } else {
            textStyler.showErrorMessage(downloadFileFeedbackMessage, result.errorMessage());

        }
    }

    /**
     * Helper function to orchestrate the process of selecting the file to upload.
     */
    private void selectFileToUpload() {
        Window window = selectFileToUploadButton.getScene().getWindow();
        fileToUpload = fileChooser.showOpenDialog(window);

        if(fileToUpload != null) {
            selectFileToUploadPath.setText(fileToUpload.getName());
            filenameToSave.setText(fileToUpload.getName());

        } else {
            resetFileToUpload();
        }
    }

    /**
     * Helper function to orchestrate the process of uploading file.
     */
    private void uploadFile() {

        if(fileToUpload == null) {
            textStyler.showErrorMessage(uploadFileFeedbackArea, "Please select a file to upload.");
            return;
        }

        String filenameToSaveAs = filenameToSave.getText();

        if(filenameToSaveAs.isBlank()) {
            textStyler.showErrorMessage(uploadFileFeedbackArea, "Please enter a valid file name, file name should not be blank.");
            return;
        }

        UploadFileRequest request = new UploadFileRequest(sessionManager.getSessionId(), fileToUpload, filenameToSaveAs);
        UploadFileResult result = userModel.uploadFile(request);

        if(result.success()) {
            textStyler.showSuccessMessage(uploadFileFeedbackArea, "File successfully uploaded.");
            downloadFileListView.getItems().add(filenameToSaveAs); // Successful file upload means file can be downloaded as well

        } else {
            textStyler.showErrorMessage(uploadFileFeedbackArea, result.errorMessage());

        }

        resetFileToUpload();
    }

    /**
     * Helper function to reset file to upload states.
     */
    private void resetFileToUpload() {
        fileToUpload = null;
        selectFileToUploadPath.setText(null);
        filenameToSave.setText(null);
    }

    @Override
    public void onEnter() {
        downloadFileListView.getItems().clear();

        downloadFileFeedbackMessage.setText(null);
        uploadFileFeedbackArea.setText(null);
        resetFileToUpload();

        usernameDisplayField.setText(sessionManager.getUsername());
        reloadDownloadableFiles();
    }
    
}
