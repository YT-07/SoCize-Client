package com.socize.pages.fileserver.admin.usermanagement;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.pages.PageController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class UserManagementController extends PageController implements Initializable {

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button viewUserDetailButton;

    @FXML
    private Label accountEmailField;

    @FXML
    private Label accountPhoneNumberField;

    @FXML
    private Label accountUsernameField;

    @FXML
    private Label feedbackMessageField;

    @FXML
    private Label usernameDisplayField;

    @FXML
    private ListView<String> userListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @Override
    public void onEnter() {
        
    }
    
}
