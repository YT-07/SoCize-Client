package com.socize.pages.fileserver.user.model;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.Header;
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
import com.socize.api.uploadfile.UploadFileApi;
import com.socize.api.uploadfile.dto.UploadFileRequest;
import com.socize.pages.fileserver.user.dto.DeleteFileResult;
import com.socize.pages.fileserver.user.dto.DownloadFileResult;
import com.socize.pages.fileserver.user.dto.GetDownloadableFilesApiResult;
import com.socize.pages.fileserver.user.dto.UploadFileResult;
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
    private final UploadFileApi uploadFileApi;

    public DefaultUserModel
    (
        LogoutService logoutService, 
        GetDownloadableFilesApi getDownloadableFilesApi, 
        ObjectMapper objectMapper, 
        DeleteFileApi deleteFileApi, 
        DownloadFileApi downloadFileApi, 
        UploadFileApi uploadFileApi
    ) 
    {
        this.logoutService = logoutService;
        this.getDownloadableFilesApi = getDownloadableFilesApi;
        this.observableList = FXCollections.observableArrayList();
        this.objectMapper = objectMapper;
        this.deleteFileApi = deleteFileApi;
        this.downloadFileApi = downloadFileApi;
        this.uploadFileApi = uploadFileApi;
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

            HttpEntity entity = response.getEntity();

            try {

                String jsonResponse = EntityUtils.toString(entity);
                GetDownloadableFilesApiResult result = objectMapper.readValue(jsonResponse, GetDownloadableFilesApiResult.class);

                return result;

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }

            }

        } catch (Exception e) {
            logger.error("Exception occured while retrieving downloadable files for the user.", e);
            return getDefaultDownloadableFilesApiResult();
        }
    }    

    @Override
    public DeleteFileResult deleteFile(DeleteFileRequest request) {
        
        try(CloseableHttpResponse response = deleteFileApi.deleteFile(request)) {

            HttpEntity entity = response.getEntity();

            try {

                String jsonResponse = EntityUtils.toString(entity);
                DeleteFileResult result = objectMapper.readValue(jsonResponse, DeleteFileResult.class);

                return result;

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }

            }

        } catch (Exception e) {
            logger.error("Exception occured while requesting for file deletion.", e);
            return getDefaultDeleteFileResult();
        }
    }

    @Override
    public DownloadFileResult downloadFile(DownloadFileRequest request, File pathToSaveFile) {
        
        try(CloseableHttpResponse response = downloadFileApi.downloadFile(request)) {

            HttpEntity entity = response.getEntity();

            try {

                Header contentTypeHeader = response.getFirstHeader("Content-Type");

                if(contentTypeHeader == null) {
                    throw new IllegalArgumentException("Content-Type header not present when downloading file.");
                }

                String contentType = contentTypeHeader.getValue();

                // TODO: Can abstract these to use stategy pattern instead if time allows
                if(contentType == null) {
                    throw new IllegalArgumentException("Content-Type header is present but value is null when downloading file.");

                } else if(contentType.equalsIgnoreCase("application/json")) {

                    String json = EntityUtils.toString(entity);
                    DownloadFileResult result = objectMapper.readValue(json, DownloadFileResult.class);

                    return result;

                } else if(contentType.equalsIgnoreCase("application/octet-stream")) {

                    InputStream inputStream = entity.getContent();
                    Files.copy(inputStream, pathToSaveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    return new DownloadFileResult(true, null);

                } else {
                    throw new IllegalArgumentException("Unexpected content type '" + contentType + "' received when downloading file, the response cannot be processed.");

                }

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }

            }

        } catch(Exception e) {
            logger.error("Exception occured while downloading file.", e);
            return getDefaultDownloadFileResult();
        }
    }

    @Override
    public UploadFileResult uploadFile(UploadFileRequest request) {
        
        try(CloseableHttpResponse response = uploadFileApi.uploadFile(request)) {

            HttpEntity entity = response.getEntity();

            try {

                String jsonResponse = EntityUtils.toString(entity);
                UploadFileResult result = objectMapper.readValue(jsonResponse, UploadFileResult.class);

                return result;

            } finally {

                if(entity != null) {
                    EntityUtils.consume(entity);
                }
                
            }

        } catch (Exception e) {
            logger.error("Exception occured when uploading file.", e);
            return getDefaultUploadFileResult();
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

    /**
     * Gets the default upload file result if anything went wrong.
     * 
     * @return the default result
     */
    private static UploadFileResult getDefaultUploadFileResult() {
        return new UploadFileResult(false, "Something went wrong, unable to upload provided file.");
    }
}
