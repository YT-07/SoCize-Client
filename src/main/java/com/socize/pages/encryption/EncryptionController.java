package com.socize.pages.encryption;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.socize.encryption.spi.EncryptionService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

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

    private final FileChooser fileChooser;
    private final DirectoryChooser directoryChooser;
    private final EncryptionService encryptionService;

    private File folderToSave;
    private File fileToEncrypt;

    public EncryptionController(EncryptionService encryptionService) {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle("Select file to encrypt");

        this.directoryChooser = new DirectoryChooser();
        this.directoryChooser.setTitle("Select folder to save");

        this.encryptionService = encryptionService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectFileToEncryptButton.setOnAction(e -> selectFileToEncrypt());

        selectFolderToSaveButton.setOnAction(e -> selectFolderToSave());

        encryptButton.setOnAction(e -> encrypt());
    }
    
    /**
     * Helper function to orchestrate the process of selecting 
     * the file to encrypt.
     */
    private void selectFileToEncrypt() {
        Window window = selectFileToEncryptButton.getScene().getWindow();
        fileToEncrypt = fileChooser.showOpenDialog(window);

        fileToEncryptPath.setText(fileToEncrypt.getAbsolutePath());
    }

    /**
     * Helper function to orchestrate the process of selecting 
     * the folder to save.
     */
    private void selectFolderToSave() {
        Window window = selectFolderToSaveButton.getScene().getWindow();
        folderToSave = directoryChooser.showDialog(window);

        folderToSavePath.setText(folderToSave.getAbsolutePath());
    }

    /**
     * Helper function to orchestrate the encryption process.
     */
    private void encrypt() {
        if(fileToEncrypt == null) {
            encryptFeedbackField.setText("Please select a file to encrypt.");
            return;
        }

        if(folderToSave == null) {
            encryptFeedbackField.setText("Please select a folder to save.");
            return;
        }

        try {

            encryptionService.encryptFile(fileToEncrypt, folderToSave);
            encryptFeedbackField.setText("File successfully encrypted.");

        } catch (Exception e) {
            encryptFeedbackField.setText(e.getMessage());
        }
    }
}
