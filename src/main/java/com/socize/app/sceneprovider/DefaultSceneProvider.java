package com.socize.app.sceneprovider;

import java.util.HashMap;

import com.socize.app.sceneprovider.appscenes.AppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.app.sceneprovider.scenefactory.DefaultSceneFactory;
import com.socize.app.sceneprovider.scenefactory.SceneFactory;
import com.socize.pages.PageController;

public class DefaultSceneProvider implements SceneProvider {
    private final HashMap<AppScenes, SceneResult<PageController>> scenes;
    private final SceneFactory sceneFactory;

    private DefaultSceneProvider(SceneFactory sceneFactory) {
        scenes = new HashMap<>();
        this.sceneFactory = sceneFactory;
    }

    private static class SingletonInstanceHolder {
        private static final DefaultSceneProvider INSTANCE = new DefaultSceneProvider(new DefaultSceneFactory());
    } 

    public static DefaultSceneProvider getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    // For testing, for injecting a mock scene factory
    static DefaultSceneProvider createTestingProvider (SceneFactory sceneFactory) {
        return new DefaultSceneProvider(sceneFactory);
    }

    @Override
    public SceneResult<PageController> getScene(AppScenes scene) {
        
        if(!scenes.containsKey(scene)) {
            SceneResult<PageController> parent = sceneFactory.load(scene);
            scenes.put(scene, parent);
        }

        return scenes.get(scene);
    }
    
}
