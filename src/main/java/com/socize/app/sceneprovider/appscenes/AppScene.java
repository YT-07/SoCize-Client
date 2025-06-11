package com.socize.app.sceneprovider.appscenes;

import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultAdminControllerFactory;
import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultDecryptionControllerFactory;
import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultEncryptionControllerFactory;
import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultHomeControllerFactory;
import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultMainMenuControllerFactory;
import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultSignInControllerFactory;
import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultSignUpControllerFactory;
import com.socize.app.sceneprovider.scenecontrollerfactory.impl.DefaultUserControllerFactory;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
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
    ENCRYPTION_PAGE(new DefaultEncryptionControllerFactory(), FilePath.ENCRYPTION_PAGE_FXML),
    DECRYPTION_PAGE(new DefaultDecryptionControllerFactory(), FilePath.DECRYPTION_PAGE_FXML),
    ADMIN_PAGE(new DefaultAdminControllerFactory(), FilePath.ADMIN_PAGE_FXML),
    USER_PAGE(new DefaultUserControllerFactory(), FilePath.USER_PAGE_FXML);

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