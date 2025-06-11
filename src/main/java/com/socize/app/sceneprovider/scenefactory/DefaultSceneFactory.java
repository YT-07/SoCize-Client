package com.socize.app.sceneprovider.scenefactory;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.socize.app.sceneprovider.appscenes.AppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class DefaultSceneFactory implements SceneFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSceneFactory.class);

    @Override
    public SceneResult<PageController> load(AppScenes scene) {
        URL scenePath = getClass().getResource(scene.getPath());
        FXMLLoader loader = new FXMLLoader(scenePath);

        SceneControllerFactory controllerFactory = scene.getControllerFactory();
        PageController controller = controllerFactory.createDefault();
        loader.setController(controller);

        Parent parent;

        try {

            parent = loader.load();

        } catch (Exception e) {
            logger.error("Error loading scene '{}'", scene.name(), e);
            throw new RuntimeException();
        }

        return new SceneResult<PageController>(parent, controller);
    }
    
}
