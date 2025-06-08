package com.socize.pages.decryption;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.socize.encryption.spi.DecryptionService;
import com.socize.utilities.textstyler.spi.TextStyler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class DecryptionController implements Initializable {

    @FXML
    private Button selectFileToDecryptButton;

    @FXML
    private Button selectEncryptionKeyButton;

    @FXML
    private Button selectFileToSaveButton;

    @FXML
    private Button decryptButton;

    @FXML
    private Label fileToDecryptPath;

    @FXML
    private Label encryptionKeyPath;

    @FXML
    private Label fileToSavePath;

    @FXML
    private Label decryptFeedbackField;

    private final TextStyler textStyler;
    private final DecryptionService decryptionService;
    private final FileChooser fileChooser;

    private File fileToDecrypt;
    private File encryptionKeyFile;
    private File fileToSave;

    public DecryptionController(TextStyler textStyler, DecryptionService decryptionService) {
        this.textStyler = textStyler;
        this.decryptionService = decryptionService;

        fileChooser = new FileChooser();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectFileToDecryptButton.setOnAction(e -> selectFileToDecrypt());

        selectEncryptionKeyButton.setOnAction(e -> selectEncryptionKey());

        selectFileToSaveButton.setOnAction(e -> selectFileToSave());

        decryptButton.setOnAction(e -> decrypt());
    }

    /**
     * Helper function to orchestrate the process of selecting 
     * the file to decrypt.
     */
    private void selectFileToDecrypt() {
        Window window = selectFileToDecryptButton.getScene().getWindow();
        
        fileChooser.setTitle("Select file to decrypt");
        fileToDecrypt = fileChooser.showOpenDialog(window);

        if(fileToDecrypt != null) {
            fileToDecryptPath.setText(fileToDecrypt.getName());

        } else {
            fileToDecryptPath.setText(null);
        }
    }
    
    /**
     * Helper function to orchestrate the process of selecting 
     * the encryption key file.
     */
    private void selectEncryptionKey() {
        Window window = selectEncryptionKeyButton.getScene().getWindow();

        fileChooser.setTitle("Select encryption key file");
        encryptionKeyFile = fileChooser.showOpenDialog(window);

        if(encryptionKeyFile != null) {
            encryptionKeyPath.setText(encryptionKeyFile.getName());

        } else {
            encryptionKeyPath.setText(null);

        }
    }

    /**
     * Helper function to orchestrate the process of selecting 
     * where to save the decrypted file.
     */
    private void selectFileToSave() {
        Window window = selectFileToSaveButton.getScene().getWindow();

        fileChooser.setTitle("Select the file to save the decrypted data");
        fileToSave = fileChooser.showSaveDialog(window);

        if(fileToSave != null) {
            fileToSavePath.setText(fileToSave.getName());

        } else {
            fileToSavePath.setText(null);

        }
    }

    /**
     * Helper function to orchestrate the decryption process.
     */
    private void decrypt() {
        if(fileToDecrypt == null) {
            textStyler.showErrorMessage(decryptFeedbackField, "Please select a file to decrypt.");
            return;
        }

        if(encryptionKeyFile == null) {
            textStyler.showErrorMessage(decryptFeedbackField, "Please select the encryption key file.");
            return;
        }

        if(fileToSave == null) {
            textStyler.showErrorMessage(decryptFeedbackField, "Please select a file to save the decrypted data.");
            return;
        }

        try {

            decryptionService.decryptFile(fileToDecrypt, encryptionKeyFile, fileToSave);
            textStyler.showSuccessMessage(decryptFeedbackField, "File successfully decrypted.");

        } catch (Exception e) {
            textStyler.showErrorMessage(decryptFeedbackField, e.getMessage());
        }
    }
}
