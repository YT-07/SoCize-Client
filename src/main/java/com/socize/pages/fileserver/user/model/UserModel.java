package com.socize.pages.fileserver.user.model;

import com.socize.api.logout.dto.LogoutRequest;

import javafx.scene.control.ListView;

public interface UserModel {

    /**
     * Logs the user out of the current session.
     * 
     * @param logoutRequest the logout request that contains all necessary data to logout.
     */
    void logout(LogoutRequest logoutRequest);

    /**
     * Sets an observable list that contains all downloadable files for this user 
     * to the provided {@code listView}
     * 
     * @param listView the listview to set the observable list on
     * @see javafx.scene.control.ListView#setItems(javafx.collections.ObservableList) ListView.setItems()
     */
    void setFileListToListView(ListView<String> listView);

    /**
     * Reload the downloadable files and updates the file list accordingly.
     */
    void reloadDownloadableFiles();
}