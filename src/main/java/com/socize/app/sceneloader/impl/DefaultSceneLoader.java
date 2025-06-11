package com.socize.app.sceneloader.impl;

import java.util.HashMap;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.dto.SceneResult;
import com.socize.app.sceneloader.scenefactory.DefaultSceneFactory;
import com.socize.app.sceneloader.scenefactory.SceneFactory;
import com.socize.app.sceneloader.spi.SceneLoader;
import com.socize.pages.TransitionablePage;

public class DefaultSceneLoader implements SceneLoader {
    private final HashMap<AppScene, SceneResult<TransitionablePage>> scenes;
    private final SceneFactory sceneFactory;

    private DefaultSceneLoader(SceneFactory sceneFactory) {
        scenes = new HashMap<>();
        this.sceneFactory = sceneFactory;
    }

    private static class SingletonInstanceHolder {
        private static final DefaultSceneLoader INSTANCE = new DefaultSceneLoader(new DefaultSceneFactory());
    } 

    public static DefaultSceneLoader getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    // For testing, for injecting a mock scene factory
    static DefaultSceneLoader createTestingLoader(SceneFactory sceneFactory) {
        return new DefaultSceneLoader(sceneFactory);
    }

    @Override
    public SceneResult<TransitionablePage> getScene(AppScene scene) {
        
        if(!scenes.containsKey(scene)) {
            SceneResult<TransitionablePage> parent = sceneFactory.load(scene);
            scenes.put(scene, parent);
        }

        return scenes.get(scene);
    }
    
}
