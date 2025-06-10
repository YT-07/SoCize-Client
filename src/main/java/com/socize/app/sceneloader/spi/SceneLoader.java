package com.socize.app.sceneloader.spi;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.dto.SceneResult;
import com.socize.pages.TransitionablePage;

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
    SceneResult<TransitionablePage> getScene(AppScene scene);
}