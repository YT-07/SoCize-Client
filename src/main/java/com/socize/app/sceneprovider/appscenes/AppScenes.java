package com.socize.app.sceneprovider.appscenes;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;

public interface AppScenes {

    /**
     * Gets the controller factory for this scene.
     * 
     * @return the controller factory
     */
    SceneControllerFactory getControllerFactory();

    /**
     * Gets the file path of the scene's file.
     * 
     * @return the file path
     */
    String getPath();

    /**
     * Gets the name of the scene.
     * 
     * @return the name of the scene
     */
    String name();
}