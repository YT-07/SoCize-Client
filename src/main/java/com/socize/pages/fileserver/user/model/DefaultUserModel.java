package com.socize.pages.fileserver.user.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socize.api.deletefile.DeleteFileApi;
import com.socize.api.deletefile.dto.DeleteFileRequest;
import com.socize.api.downloadfile.DownloadFileApi;
import com.socize.api.downloadfile.dto.DownloadFileRequest;
import com.socize.api.getdownloadablefiles.GetDownloadableFilesApi;
import com.socize.api.getdownloadablefiles.dto.GetDownloadableFilesRequest;
import com.socize.api.logout.dto.LogoutRequest;
import com.socize.pages.fileserver.user.dto.DeleteFileResult;
import com.socize.pages.fileserver.user.dto.DownloadFileResult;
import com.socize.pages.fileserver.user.dto.GetDownloadableFilesApiResult;
import com.socize.pages.fileserver.utilities.logoutservice.LogoutService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DefaultUserModel implements UserModel {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserModel.class);

    private final LogoutService logoutService;
    private final ObservableList<String> observableList;
    private final ObjectMapper objectMapper;

    private final GetDownloadableFilesApi getDownloadableFilesApi;
    private final DeleteFileApi deleteFileApi;
    private final DownloadFileApi downloadFileApi;

    public DefaultUserModel
    (
        LogoutService logoutService, 
        GetDownloadableFilesApi getDownloadableFilesApi, 
        ObjectMapper objectMapper, 
        DeleteFileApi deleteFileApi, 
        DownloadFileApi downloadFileApi
    ) 
    {
        this.logoutService = logoutService;
        this.getDownloadableFilesApi = getDownloadableFilesApi;
        this.observableList = FXCollections.observableArrayList();
        this.objectMapper = objectMapper;
        this.deleteFileApi = deleteFileApi;
        this.downloadFileApi = downloadFileApi;
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
    public GetDownloadableFilesApiResult getDownloadableFiles(GetDownloadableFilesRequest request) {
        
        try(CloseableHttpResponse response = getDownloadableFilesApi.getDownloadableFiles(request)) {

            String jsonResponse = EntityUtils.toString(response.getEntity());
            GetDownloadableFilesApiResult result = objectMapper.readValue(jsonResponse, GetDownloadableFilesApiResult.class);

            return result;

        } catch (Exception e) {
            logger.error("Exception occured while retrieving downloadable files for the user.", e);
            return getDefaultDownloadableFilesApiResult();
        }
    }    

    @Override
    public DeleteFileResult deleteFile(DeleteFileRequest request) {
        
        try(CloseableHttpResponse response = deleteFileApi.deleteFile(request)) {

            String jsonResponse = EntityUtils.toString(response.getEntity());
            DeleteFileResult result = objectMapper.readValue(jsonResponse, DeleteFileResult.class);

            return result;

        } catch (Exception e) {
            logger.error("Exception occured while requesting for file deletion.", e);
            return getDefaultDeleteFileResult();
        }
    }

    @Override
    public DownloadFileResult downloadFile(DownloadFileRequest request, File pathToSaveFile) {
        
        try(CloseableHttpResponse response = downloadFileApi.downloadFile(request)) {

            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();

            // If time allows, could decouple the file saving logic here
            try(OutputStream outputStream = new FileOutputStream(pathToSaveFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;

                while(true) {

                    bytesRead = inputStream.read(buffer);

                    if(bytesRead < 1) {
                        break;
                    }

                    outputStream.write(buffer, 0, bytesRead);
                }

            }

            EntityUtils.consume(entity);
            return new DownloadFileResult(true, null);

        } catch(Exception e) {
            logger.error("Exception occured while downloading file.", e);
            return getDefaultDownloadFileResult();
        }
    }

    /**
     * Gets the default downloadable files result if anything went wrong.
     * 
     * @return the default result
     */
    private static GetDownloadableFilesApiResult getDefaultDownloadableFilesApiResult() {
        return new GetDownloadableFilesApiResult(false, "Something went wrong, unable to retrieve files.", null);
    }

    /**
     * Gets the default delete file result if anything went wrong.
     * 
     * @return the default result
     */
    private static DeleteFileResult getDefaultDeleteFileResult() {
        return new DeleteFileResult(false, "Something went wrong, unable to delete file.");
    }

    /**
     * Gets the default download file result if anything went wrong.
     * 
     * @return the default result
     */
    private static DownloadFileResult getDefaultDownloadFileResult() {
        return new DownloadFileResult(false, "Something went wrong, unable to download file.");
    }
}
