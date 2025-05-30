package com.socize.shared.spi;

import java.util.function.Consumer;

import com.socize.app.sceneloader.AppScene;

public interface MainMenuPageState {

    /**
     * Subscribes to the event when the scene changes
     * 
     * @param observer the method to execute when the event occurs.
     */
    void subscribe(Consumer<AppScene> observer);

    /**
     * Unsubscribe from the event.
     * 
     * @param observer the method to unsubscribe
     */
    void unsubscribe(Consumer<AppScene> observer);

    /**
     * Sets the new page for this shared state.
     * 
     * @param scene the scene to update to
     */
    void setPage(AppScene scene);
}