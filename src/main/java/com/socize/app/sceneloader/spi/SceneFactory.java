package com.socize.app.sceneloader.spi;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.dto.SceneResult;
import com.socize.pages.TransitionablePage;

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
    SceneResult<TransitionablePage> load(AppScene scene);
}