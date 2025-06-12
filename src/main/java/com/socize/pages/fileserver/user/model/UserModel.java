package com.socize.pages.fileserver.user.model;

import com.socize.api.logout.dto.LogoutRequest;

import javafx.collections.ObservableList;

public interface UserModel {

    /**
     * Logs the user out of the current session.
     * 
     * @param logoutRequest the logout request that contains all necessary data to logout.
     */
    void logout(LogoutRequest logoutRequest);

    /**
     * Gets the observable list that contains all downloadable files for the user.
     * 
     * @return the observable list
     */
    ObservableList<String> getDownloadableFileList();

    /**
     * Reload the downloadable files and updates the file list accordingly.
     */
    void reloadDownloadableFiles();
}