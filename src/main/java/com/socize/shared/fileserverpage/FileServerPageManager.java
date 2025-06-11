package com.socize.shared.fileserverpage;

import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;

public interface FileServerPageManager {

    void setLoginStatus(LoginStatus loginStatus);
    LoginStatus getLoginStatus();

    public static enum LoginStatus {
        NOT_LOGGED_IN(DefaultAppScenes.HOME_PAGE),
        USER(DefaultAppScenes.USER_PAGE),
        ADMIN(DefaultAppScenes.ADMIN_PAGE);

        private DefaultAppScenes fileServerPage;

        private LoginStatus(DefaultAppScenes fileServerPage) {
            this.fileServerPage = fileServerPage;
        }
        
        public DefaultAppScenes getFileServerPage() {
            return fileServerPage;
        }
    }

}