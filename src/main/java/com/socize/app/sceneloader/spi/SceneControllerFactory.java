package com.socize.app.sceneloader.spi;

/**
 * Interface to provide the default controller for a scene.
 */
public interface SceneControllerFactory {

    /**
     * Gets the default controller for a scene.
     * 
     * @return the controller
     */
    Object createDefault();
}