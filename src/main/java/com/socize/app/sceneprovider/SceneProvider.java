package com.socize.app.sceneprovider;

import com.socize.app.sceneprovider.appscenes.AppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.pages.PageController;

/**
 * Interface to provide scene loading services.
 */
public interface SceneProvider {

    /**
     * Gets the specified scene object.
     * 
     * @param scene the scene to retrieve
     * @return the requested scene
     */
    SceneResult<PageController> getScene(AppScenes scene);
}