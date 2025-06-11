package com.socize.app;

import com.socize.app.sceneprovider.AppScene;
import com.socize.app.sceneprovider.SceneLoader;
import com.socize.app.sceneprovider.SceneLoaders;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.pages.TransitionablePage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneLoader loader = SceneLoaders.getDefault();

        SceneResult<TransitionablePage> sceneResult = loader.getScene(AppScene.MAIN_PAGE);
        Scene mainScene = new Scene(sceneResult.parent());
        sceneResult.controller().onEnter();

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
}
