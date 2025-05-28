package com.socize.app.sceneloader.impl;

import java.util.HashMap;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.spi.SceneFactory;
import com.socize.app.sceneloader.spi.SceneLoader;

import javafx.scene.Parent;

public class DefaultSceneLoader implements SceneLoader {
    private static DefaultSceneLoader instance;

    private final HashMap<AppScene, Parent> scenes;
    private final SceneFactory sceneFactory;

    private DefaultSceneLoader(SceneFactory sceneFactory) {
        scenes = new HashMap<>();
        this.sceneFactory = sceneFactory;
    }

    public static DefaultSceneLoader getInstance() {
        if(instance == null) {
            instance = new DefaultSceneLoader(new DefaultSceneFactory());
        }

        return instance;
    }

    // For testing, for injecting a mock scene factory
    static DefaultSceneLoader createTestingLoader(SceneFactory sceneFactory) {
        return new DefaultSceneLoader(sceneFactory);
    }

    @Override
    public Parent getScene(AppScene scene) {
        
        if(!scenes.containsKey(scene)) {
            Parent parent = sceneFactory.load(scene);
            scenes.put(scene, parent);
        }

        return scenes.get(scene);
    }
    
}
