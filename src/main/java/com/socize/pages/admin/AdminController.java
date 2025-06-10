package com.socize.pages.admin;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.pages.TransitionablePage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class AdminController implements Initializable, TransitionablePage {

    @FXML
    private Button userManagementButton;

    @FXML
    private Button serverHealthcheckButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @Override
    public void onEnter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnter'");
    }

    @Override
    public void onExit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onExit'");
    }
    
}
