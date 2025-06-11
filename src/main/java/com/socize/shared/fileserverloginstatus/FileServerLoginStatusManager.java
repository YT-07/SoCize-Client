package com.socize.shared.fileserverloginstatus;

import com.socize.app.sceneprovider.appscenes.AppScene;

public interface FileServerLoginStatusManager {

    void setLoginStatus(LoginStatus loginStatus);
    LoginStatus getLoginStatus();

    public static enum LoginStatus {
        NOT_LOGGED_IN(AppScene.HOME_PAGE),
        USER(AppScene.USER_PAGE),
        ADMIN(AppScene.ADMIN_PAGE);

        private AppScene fileServerPage;

        private LoginStatus(AppScene fileServerPage) {
            this.fileServerPage = fileServerPage;
        }
        
        public AppScene getFileServerPage() {
            return fileServerPage;
        }
    }

}