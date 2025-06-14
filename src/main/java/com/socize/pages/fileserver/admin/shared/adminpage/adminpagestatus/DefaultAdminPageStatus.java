package com.socize.pages.fileserver.admin.shared.adminpage.adminpagestatus;

import com.socize.app.sceneprovider.appscenes.AppScenes;
import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;

public enum DefaultAdminPageStatus implements AdminPageStatus {
    USER_MANAGEMENT(DefaultAppScenes.USER_MANAGEMENT),
    SERVER_HEALTHCHECK(DefaultAppScenes.SERVER_HEALTHCHECK);

    private final AppScenes appScenes;

    private DefaultAdminPageStatus(AppScenes appScenes) {
        this.appScenes = appScenes;
    }

    @Override
    public AppScenes getAdminPage() {
        return appScenes;
    }
    
}
