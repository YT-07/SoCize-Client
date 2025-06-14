package com.socize.pages.fileserver.admin.shared.adminpage;

import java.util.function.Consumer;

import com.socize.pages.fileserver.admin.shared.adminpage.adminpagestatus.AdminPageStatus;

public interface AdminPageStatusManager {

    /**
     * Subscribes to the event when the admin page changes.
     * 
     * @param observer the method to execute when the event occurs
     */
    void subscribe(Consumer<AdminPageStatus> observer);

    /**
     * Unsubscribe from the event.
     * 
     * @param observer the method to unsubscribe
     */
    void unsubscribe(Consumer<AdminPageStatus> observer);

    /**
     * Sets the new admin page
     * 
     * @param adminPageStatus the new admin page
     */
    void setPage(AdminPageStatus adminPageStatus);
}