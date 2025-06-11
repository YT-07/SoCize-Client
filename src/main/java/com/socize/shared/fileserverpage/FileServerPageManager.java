package com.socize.shared.fileserverpage;

import com.socize.shared.fileserverpage.fileserverpagestatus.FileServerPageStatus;

public interface FileServerPageManager {

    void setStatus(FileServerPageStatus status);
    FileServerPageStatus getStatus();

}