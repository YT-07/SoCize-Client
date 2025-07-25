package com.socize.app.sceneprovider.scenefactory;

import com.socize.app.sceneprovider.appscenes.AppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.pages.PageController;

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
    SceneResult<PageController> load(AppScenes scene);
}