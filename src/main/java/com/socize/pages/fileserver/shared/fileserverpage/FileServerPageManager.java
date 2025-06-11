package com.socize.pages.fileserver.shared.fileserverpage;

import com.socize.pages.fileserver.shared.fileserverpage.fileserverpagestatus.FileServerPageStatus;

public interface FileServerPageManager {

    void setStatus(FileServerPageStatus status);
    FileServerPageStatus getStatus();

}