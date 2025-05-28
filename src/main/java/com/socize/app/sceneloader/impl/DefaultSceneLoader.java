package com.socize.app.sceneloader.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.app.sceneloader.spi.SceneLoader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class DefaultSceneLoader implements SceneLoader {
    private static DefaultSceneLoader instance;
    private static final Logger logger = LoggerFactory.getLogger(DefaultSceneLoader.class);

    private HashMap<AppScene, Parent> scenes;

    private DefaultSceneLoader() {
        scenes = new HashMap<>();
    }

    public static DefaultSceneLoader getInstance() {
        if(instance == null) {
            instance = new DefaultSceneLoader();
        }

        return instance;
    }

    @Override
    public Parent getScene(AppScene scene) {
        
        if(!scenes.containsKey(scene)) {
            URL scenePath = getClass().getResource(scene.getPath());
            FXMLLoader loader = new FXMLLoader(scenePath);

            SceneControllerFactory controllerFactory = scene.getControllerFactory();
            loader.setController(controllerFactory.createDefault());

            try {

                Parent parent = loader.load();
                scenes.put(scene, parent);

            } catch (IOException ioe) {
                logger.error("Error loading scene '{}'.", scene.name(), ioe);

                throw new RuntimeException();

            }

        }

        return scenes.get(scene);
    }
    
}
