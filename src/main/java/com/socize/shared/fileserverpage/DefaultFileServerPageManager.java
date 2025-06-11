package com.socize.shared.fileserverpage;

import com.socize.shared.fileserverpage.fileserverpagestatus.DefaultFileServerPageStatus;
import com.socize.shared.fileserverpage.fileserverpagestatus.FileServerPageStatus;

public class DefaultFileServerPageManager implements FileServerPageManager {
    private FileServerPageStatus status;
    private final Object loginStatusLock;

    private DefaultFileServerPageManager() {
        status = DefaultFileServerPageStatus.AT_HOME; // default page
        loginStatusLock = new Object();
    }

    private static class SingletonInstanceHolder {
        private static final DefaultFileServerPageManager INSTANCE = new DefaultFileServerPageManager();
    }

    public static DefaultFileServerPageManager getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void setStatus(FileServerPageStatus status) {
        
        synchronized(loginStatusLock) {
            this.status = status;
        }

    }

    @Override
    public FileServerPageStatus getStatus() {
        return status;
    }
    
}
