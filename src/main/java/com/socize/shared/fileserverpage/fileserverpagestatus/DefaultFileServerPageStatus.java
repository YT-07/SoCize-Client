package com.socize.shared.fileserverpage.fileserverpagestatus;

import com.socize.app.sceneprovider.appscenes.AppScenes;
import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;

public enum DefaultFileServerPageStatus implements FileServerPageStatus {
    SIGNING_IN(DefaultAppScenes.SIGN_IN_PAGE),
    SIGNING_UP(DefaultAppScenes.SIGN_UP_PAGE),
    AT_HOME(DefaultAppScenes.HOME_PAGE),
    AT_USER_PAGE(DefaultAppScenes.USER_PAGE),
    AT_ADMIN_PAGE(DefaultAppScenes.ADMIN_PAGE);

    private AppScenes appScene;

    private DefaultFileServerPageStatus(AppScenes appScenes) {
        this.appScene = appScenes;
    }

    @Override
    public AppScenes getFileServerPage() {
        return appScene;
    }

    
}