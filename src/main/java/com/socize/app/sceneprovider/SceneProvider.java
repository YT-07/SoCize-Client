package com.socize.app.sceneprovider;

import com.socize.app.sceneprovider.appscenes.AppScene;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.pages.TransitionablePage;

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
    SceneResult<TransitionablePage> getScene(AppScene scene);
}