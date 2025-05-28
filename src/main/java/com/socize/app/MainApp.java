package com.socize.app;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.SceneLoaders;
import com.socize.app.sceneloader.spi.SceneLoader;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneLoader loader = SceneLoaders.getDefault();

        Parent parent = loader.getScene(AppScene.MAIN_PAGE);
        Scene mainScene = new Scene(parent);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
}
