package com.socize.pages.fileserver.admin;

import java.net.URL;
import java.util.ResourceBundle;

import com.socize.app.sceneprovider.SceneProvider;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.admin.shared.adminpage.AdminPageStatusManager;
import com.socize.pages.fileserver.admin.shared.adminpage.adminpagestatus.DefaultAdminPageStatus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class AdminController extends PageController implements Initializable {

    @FXML
    private Button userManagementButton;

    @FXML
    private Button serverHealthcheckButton;

    @FXML
    private BorderPane borderPane;

    private final AdminPageStatusManager adminPageStatusManager;
    private final SceneProvider sceneProvider;

    public AdminController(AdminPageStatusManager adminPageStatusManager, SceneProvider sceneProvider) {
        this.adminPageStatusManager = adminPageStatusManager;
        this.sceneProvider = sceneProvider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminPageStatusManager.subscribe(pageStatus -> {
            SceneResult<PageController> scene = sceneProvider.getScene(pageStatus.getAdminPage());
            scene.controller().onEnter();
            borderPane.setCenter(scene.parent());
        });

        // Default page to display
        adminPageStatusManager.setPage(DefaultAdminPageStatus.USER_MANAGEMENT);

        userManagementButton.setOnAction(e -> {
            adminPageStatusManager.setPage(DefaultAdminPageStatus.USER_MANAGEMENT);
        });

        serverHealthcheckButton.setOnAction(e -> {
            adminPageStatusManager.setPage(DefaultAdminPageStatus.SERVER_HEALTHCHECK);
        });
    }

    @Override
    public void onEnter() {
        
    }
    
}
