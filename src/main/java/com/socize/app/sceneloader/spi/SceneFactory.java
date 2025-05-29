package com.socize.app.sceneloader.spi;

import com.socize.app.sceneloader.AppScene;

import javafx.scene.Parent;

/**
 * Interface to create new scenes
 */
public interface SceneFactory {

    /**
     * Creates a new scene specified at {@code scene}
     * 
     * @param scene the scene to create
     * @return the scene
     */
    Parent load(AppScene scene);
}