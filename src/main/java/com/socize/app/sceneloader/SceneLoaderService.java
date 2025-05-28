package com.socize.app.sceneloader;

import com.socize.app.sceneloader.impl.DefaultSceneLoader;
import com.socize.app.sceneloader.spi.SceneLoader;

/**
 * Interface to get the default scene loader for this app.
 */
public class SceneLoaderService {
    
    public static SceneLoader getLoader() {
        return DefaultSceneLoader.getInstance();
    }
}
