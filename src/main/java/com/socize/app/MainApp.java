package com.socize.app;

import com.socize.app.sceneprovider.AppScene;
import com.socize.app.sceneprovider.DefaultSceneProvider;
import com.socize.app.sceneprovider.SceneProvider;
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
        SceneProvider provider = DefaultSceneProvider.getInstance();

        SceneResult<TransitionablePage> sceneResult = provider.getScene(AppScene.MAIN_PAGE);
        Scene mainScene = new Scene(sceneResult.parent());
        sceneResult.controller().onEnter();

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
}
