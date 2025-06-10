package com.socize.config;

/**
 * Represents and holds a reference to the file path of all resource files for this app.
 */
public class FilePath {
    
    private FilePath() {
        throw new UnsupportedOperationException("Config file not meant to be instantiated.");
    }

    public static final String LOGBACK_CONFIG = "/config/logback.xml";
    
    public static final String MAIN_PAGE_FXML = "/scenes/MAINPAGE.fxml";
    public static final String SIGN_IN_PAGE_FXML = "/scenes/SIGNINPAGE.fxml";
    public static final String SIGN_UP_PAGE_FXML = "/scenes/SIGNUPPAGE.fxml";
    public static final String HOME_PAGE_FXML = "/scenes/HOMEPAGE.fxml";

    public static final String ADMIN_PAGE_FXML = "/scenes/ADMINPAGE.fxml";
    public static final String REMOVE_ACCOUNT_PAGE_FXML = "/scenes/REMOVEACCOUNT.fxml";
    public static final String SERVER_HEALTHCHECK_PAGE_FXML = "/scenes/SERVERHEALTHCHECK.fxml";

    public static final String USER_PAGE_FXML = "/scenes/USERPAGE.fxml";
    public static final String ENCRYPTION_PAGE_FXML = "/scenes/ENCRYPTIONPAGE.fxml";
    public static final String DECRYPTION_PAGE_FXML = "/scenes/DECRYPTIONPAGE.fxml";
}
