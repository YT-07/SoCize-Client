package com.socize.pages.fileserver.shared.fileserverpage;

import com.socize.pages.fileserver.shared.fileserverpage.fileserverpagestatus.FileServerPageStatus;

public interface FileServerPageManager {

    /**
     * Sets the current file server page's status.
     * 
     * @param status the status to set
     * @see com.socize.pages.fileserver.shared.fileserverpage.fileserverpagestatus.FileServerPageStatus FileServerPageStatus
     */
    void setStatus(FileServerPageStatus status);

    /**
     * Gets the current file server page's status.
     * 
     * @return the current status
     */
    FileServerPageStatus getStatus();

}