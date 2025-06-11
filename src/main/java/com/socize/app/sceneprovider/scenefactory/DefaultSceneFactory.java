package com.socize.app.sceneprovider.scenefactory;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.app.sceneprovider.appscenes.AppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.TransitionablePage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class DefaultSceneFactory implements SceneFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSceneFactory.class);

    @Override
    public SceneResult<TransitionablePage> load(AppScenes scene) {
        URL scenePath = getClass().getResource(scene.getPath());
        FXMLLoader loader = new FXMLLoader(scenePath);

        SceneControllerFactory controllerFactory = scene.getControllerFactory();
        TransitionablePage controller = controllerFactory.createDefault();
        loader.setController(controller);

        Parent parent;

        try {

            parent = loader.load();

        } catch (Exception e) {
            logger.error("Error loading scene '{}'", scene.name(), e);
            throw new RuntimeException();
        }

        return new SceneResult<TransitionablePage>(parent, controller);
    }
    
}
