package com.socize.app.sceneprovider.scenecontrollerfactory.spi;

import com.socize.pages.PageController;

/**
 * Interface to provide the default controller for a scene.
 */
public interface SceneControllerFactory {

    /**
     * Gets the default controller for a scene.
     * 
     * @return the controller
     */
    PageController createDefault();
}