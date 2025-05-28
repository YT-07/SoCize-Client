package com.socize.app.sceneloader.spi;

import com.socize.app.sceneloader.AppScene;

import javafx.scene.Parent;

/**
 * Interface to provide scene loading services.
 */
public interface SceneLoader {

    /**
     * Gets the specified scene object.
     * 
     * @param scene the scene to retrieve
     * @return the requested scene
     */
    Parent getScene(AppScene scene);
}