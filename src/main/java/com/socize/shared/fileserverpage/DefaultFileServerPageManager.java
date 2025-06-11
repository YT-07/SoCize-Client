package com.socize.shared.fileserverpage;

import com.socize.shared.fileserverpage.fileserverpagestatus.DefaultFileServerPageStatus;
import com.socize.shared.fileserverpage.fileserverpagestatus.FileServerPageStatus;
import com.socize.shared.mainmenupagestate.DefaultMainMenuPageState;
import com.socize.shared.mainmenupagestate.MainMenuPageState;

public class DefaultFileServerPageManager implements FileServerPageManager {
    private FileServerPageStatus status;

    private final MainMenuPageState mainMenuPageState;
    private final Object loginStatusLock;

    private DefaultFileServerPageManager(MainMenuPageState mainMenuPageState) {
        status = DefaultFileServerPageStatus.AT_HOME; // default page

        this.mainMenuPageState = mainMenuPageState;
        loginStatusLock = new Object();
    }

    private static class SingletonInstanceHolder {
        private static final DefaultFileServerPageManager INSTANCE = new DefaultFileServerPageManager(DefaultMainMenuPageState.getInstance());
    }

    public static DefaultFileServerPageManager getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void setStatus(FileServerPageStatus status) {
        
        synchronized(loginStatusLock) {
            this.status = status;
            mainMenuPageState.setPage(status.getFileServerPage());
        }

    }

    @Override
    public FileServerPageStatus getStatus() {
        return status;
    }
    
}
