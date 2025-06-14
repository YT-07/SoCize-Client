package com.socize.pages.fileserver.admin.serverhealthcheck;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.api.logout.dto.LogoutRequest;
import com.socize.api.serverhealthcheck.dto.ServerHealthcheckRequest;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.admin.serverhealthcheck.dto.ServerHealthcheckResult;
import com.socize.pages.fileserver.admin.serverhealthcheck.dto.ServerStatus;
import com.socize.pages.fileserver.admin.serverhealthcheck.model.ServerHealthcheckModel;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.utilities.logoutservice.LogoutService;
import com.socize.utilities.textstyler.TextStyler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ServerHealthcheckController extends PageController implements Initializable {

    @FXML
    private Button checkServerButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label cpuUsageField;

    @FXML
    private Label databaseStatusField;

    @FXML
    private Label diskSpaceAvailableField;

    @FXML
    private Label memoryUsageField;

    @FXML
    private Label usernameDisplayField;

    @FXML
    private Label checkServerStatusFeedbackField;

    private static final Logger logger = LoggerFactory.getLogger(ServerHealthcheckController.class);

    private final SessionManager sessionManager;
    private final LogoutService logoutService;
    private final ServerHealthcheckModel model;
    private final TextStyler textStyler;

    public ServerHealthcheckController
    (
        SessionManager sessionManager, 
        LogoutService logoutService, 
        ServerHealthcheckModel model, 
        TextStyler textStyler
    ) 
    {
        this.sessionManager = sessionManager;
        this.logoutService = logoutService;
        this.model = model;
        this.textStyler = textStyler;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutButton.setOnAction(e -> logout());

        checkServerButton.setOnAction(e -> getServerStatus());
    }

    /**
     * Helper function to reset all server status.
     */
    private void resetServerStatus() {
        cpuUsageField.setText("-");
        databaseStatusField.setText("-");
        diskSpaceAvailableField.setText("-");
        memoryUsageField.setText("-");
    }

    /**
     * Helper function for logging out
     */
    private void logout() {
        LogoutRequest request = new LogoutRequest(sessionManager.getSessionId());
        logoutService.logout(request);
    }

    /**
     * Helper function to orchestrate the process of getting and displaying the server status
     */
    private void getServerStatus() {
        ServerHealthcheckRequest request = new ServerHealthcheckRequest(sessionManager.getSessionId());
        ServerHealthcheckResult result = model.getServerStatus(request);

        resetServerStatus();

        if(!result.success()) {
            textStyler.showErrorMessage(checkServerStatusFeedbackField, result.errorMessage());
            return;
        }

        ServerStatus statusReceived = result.status();

        if(statusReceived == null) {
            logger.error("Server status retrieval is successful but status received is null, unable to display server status.");
            textStyler.showErrorMessage(checkServerStatusFeedbackField, "Server status successfully retrieved but there's no status to display.");
            return;
        }

        databaseStatusField.setText(statusReceived.databaseStatus());
        cpuUsageField.setText(statusReceived.cpuUsage());
        memoryUsageField.setText(statusReceived.memoryUsage());
        diskSpaceAvailableField.setText(statusReceived.diskSpaceAvailable());

        textStyler.showSuccessMessage(checkServerStatusFeedbackField, "Successfully retrieved server status.");
    }

    @Override
    public void onEnter() {
        resetServerStatus();
        checkServerStatusFeedbackField.setText(null);

        usernameDisplayField.setText(sessionManager.getUsername());
    }
    
}
