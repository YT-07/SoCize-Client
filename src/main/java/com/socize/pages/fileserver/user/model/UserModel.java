package com.socize.pages.fileserver.user.model;

import com.socize.api.getdownloadablefiles.dto.GetDownloadableFilesRequest;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.pages.fileserver.user.dto.GetDownloadableFilesApiResult;

import javafx.collections.ObservableList;

public interface UserModel {

    /**
     * Logs the user out of the current session.
     * 
     * @param logoutRequest the logout request that contains all necessary data to logout.
     */
    void logout(LogoutRequest logoutRequest);

    /**
     * Gets the observable list that will store the downloadable files for the user.
     * 
     * @return the observable list
     */
    ObservableList<String> getDownloadableFileList();

    /**
     * Attempts to retrieve all downloadable files for the user.
     * 
     * @param request the api request
     * @return the api response
     */
    GetDownloadableFilesApiResult getDownloadableFiles(GetDownloadableFilesRequest request);
}