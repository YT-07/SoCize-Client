package com.socize.pages.fileserver.user.model;

import java.io.File;

import com.socize.api.deletefile.dto.DeleteFileRequest;
import com.socize.api.downloadfile.dto.DownloadFileRequest;
import com.socize.api.getdownloadablefiles.dto.GetDownloadableFilesRequest;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.api.uploadfile.dto.UploadFileRequest;
import com.socize.pages.fileserver.user.dto.DeleteFileResult;
import com.socize.pages.fileserver.user.dto.DownloadFileResult;
import com.socize.pages.fileserver.user.dto.GetDownloadableFilesApiResult;
import com.socize.pages.fileserver.user.dto.UploadFileResult;

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

    /**
     * Attempts to request for file deletion on the server
     * 
     * @param request the file deletion request
     * @return the file deletion result
     */
    DeleteFileResult deleteFile(DeleteFileRequest request);

    /**
     * Attempts to download a file and save to {@code pathToSaveFile}
     * 
     * @param request the file download request
     * @param pathToSaveFile the path to save the file
     * @return the file download result
     */
    DownloadFileResult downloadFile(DownloadFileRequest request, File pathToSaveFile);

    /**
     * Attempts to upload the specified file.
     * 
     * @param request the file upload request
     * @return the file uplaod result
     */
    UploadFileResult uploadFile(UploadFileRequest request);
}