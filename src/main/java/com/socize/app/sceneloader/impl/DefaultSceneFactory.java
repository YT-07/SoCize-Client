package com.socize.app.sceneloader.impl;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.spi.SceneControllerFactory;
import com.socize.app.sceneloader.spi.SceneFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class DefaultSceneFactory implements SceneFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSceneFactory.class);

    @Override
    public Parent load(AppScene scene) {
        URL scenePath = getClass().getResource(scene.getPath());
        FXMLLoader loader = new FXMLLoader(scenePath);

        SceneControllerFactory controllerFactory = scene.getControllerFactory();
        loader.setController(controllerFactory.createDefault());

        Parent parent;

        try {

            parent = loader.load();

        } catch (IOException ioe) {
            logger.error("Error loading scene '{}'", scene.name(), ioe);
            throw new RuntimeException();
        }

        return parent;
    }
    
}
