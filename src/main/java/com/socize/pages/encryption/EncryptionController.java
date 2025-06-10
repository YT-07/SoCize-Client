package com.socize.pages.encryption;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.socize.encryption.spi.EncryptionService;
import com.socize.pages.TransitionablePage;
import com.socize.utilities.textstyler.spi.TextStyler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class EncryptionController implements Initializable, TransitionablePage {

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
    private final TextStyler textStyler;

    private File folderToSave;
    private File fileToEncrypt;

    public EncryptionController(EncryptionService encryptionService, TextStyler textStyler) {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle("Select file to encrypt");

        this.directoryChooser = new DirectoryChooser();
        this.directoryChooser.setTitle("Select folder to save");

        this.encryptionService = encryptionService;
        this.textStyler = textStyler;
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

        if(fileToEncrypt != null) {
            fileToEncryptPath.setText(fileToEncrypt.getName());

        } else {
            fileToEncryptPath.setText(null);

        }
    }

    /**
     * Helper function to orchestrate the process of selecting 
     * the folder to save.
     */
    private void selectFolderToSave() {
        Window window = selectFolderToSaveButton.getScene().getWindow();
        folderToSave = directoryChooser.showDialog(window);

        if(folderToSave != null) {
            folderToSavePath.setText(folderToSave.getName());

        } else {
            folderToSavePath.setText(null);
        }
    }

    /**
     * Helper function to orchestrate the encryption process.
     */
    private void encrypt() {
        if(fileToEncrypt == null) {
            textStyler.showErrorMessage(encryptFeedbackField, "Please select a file to encrypt.");
            return;
        }

        if(folderToSave == null) {
            textStyler.showErrorMessage(encryptFeedbackField, "Please select a folder to save.");
            return;
        }

        try {

            encryptionService.encryptFile(fileToEncrypt, folderToSave);
            textStyler.showSuccessMessage(encryptFeedbackField, "File successfully encrypted.");

        } catch (Exception e) {
            textStyler.showErrorMessage(encryptFeedbackField, e.getMessage());
        }
    }

    @Override
    public void onEnter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnter'");
    }

}
