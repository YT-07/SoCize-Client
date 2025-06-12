package com.socize.pages.fileserver.user.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.getdownloadablefiles.GetDownloadableFilesApi;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.pages.fileserver.utilities.logoutservice.LogoutService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DefaultUserModel implements UserModel {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserModel.class);

    private final LogoutService logoutService;
    private final GetDownloadableFilesApi getDownloadableFilesApi;
    private final ObservableList<String> observableList;
    private final ObjectMapper objectMapper;

    public DefaultUserModel(LogoutService logoutService, GetDownloadableFilesApi getDownloadableFilesApi, ObjectMapper objectMapper) {
        this.logoutService = logoutService;
        this.getDownloadableFilesApi = getDownloadableFilesApi;
        this.observableList = FXCollections.observableArrayList();
        this.objectMapper = objectMapper;
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        
        try {

            logoutService.logout(logoutRequest);

        } catch(Exception e) {
            logger.error("Something went wrong during logout.", e);
        }
    }

    @Override
    public ObservableList<String> getDownloadableFileList() {
        return observableList;
    }

    @Override
    public void reloadDownloadableFiles() {
        
    }

}
