package com.socize.app.sceneloader;

import com.socize.app.sceneloader.impl.DefaultAdminControllerFactory;
import com.socize.app.sceneloader.impl.DefaultDecryptionControllerFactory;
import com.socize.app.sceneloader.impl.DefaultEncryptionControllerFactory;
import com.socize.app.sceneloader.impl.DefaultHomeControllerFactory;
import com.socize.app.sceneloader.impl.DefaultMainMenuControllerFactory;
import com.socize.app.sceneloader.impl.DefaultSignInControllerFactory;
import com.socize.app.sceneloader.impl.DefaultSignUpControllerFactory;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.config.FilePath;

/**
 * Represent and hold references to all scenes for this app and 
 * their respective file path and controller factory.
 */
public enum AppScene {
    MAIN_PAGE(new DefaultMainMenuControllerFactory(), FilePath.MAIN_PAGE_FXML),
    SIGN_IN_PAGE(new DefaultSignInControllerFactory(), FilePath.SIGN_IN_PAGE_FXML),
    SIGN_UP_PAGE(new DefaultSignUpControllerFactory(), FilePath.SIGN_UP_PAGE_FXML),
    HOME_PAGE(new DefaultHomeControllerFactory(), FilePath.HOME_PAGE_FXML),
    ENCRYPTION_PAGE(new DefaultEncryptionControllerFactory(), FilePath.ENCRYPTION_PAGE),
    DECRYPTION_PAGE(new DefaultDecryptionControllerFactory(), FilePath.DECRYPTION_PAGE),
    ADMIN_PAGE(new DefaultAdminControllerFactory(), FilePath.ADMIN_PAGE_FXML);

    private SceneControllerFactory controllerFactory;
    private String sceneResourcePath;

    private AppScene(SceneControllerFactory controllerFactory, String sceneResourcePath) {
        this.controllerFactory = controllerFactory;
        this.sceneResourcePath = sceneResourcePath;
    }

    public SceneControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    public String getPath() {
        return sceneResourcePath;
    }

}