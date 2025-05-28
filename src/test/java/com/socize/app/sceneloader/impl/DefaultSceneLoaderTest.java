package com.socize.app.sceneloader.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.spi.SceneFactory;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class DefaultSceneLoaderTest {
    
    @Test
    void shouldCacheAndReturnSameParent_IfParentIsRequestedMultipleTimes() {
        AppScene mockScene = AppScene.MAIN_PAGE;
        
        SceneFactory mockFactory = mock(SceneFactory.class);
        when(mockFactory.load(mockScene)).thenReturn(new StackPane(), new StackPane());

        DefaultSceneLoader loader = DefaultSceneLoader.createTestingLoader(mockFactory);

        Parent first = loader.getScene(mockScene);
        Parent second = loader.getScene(mockScene);

        assertSame(first, second);
    }
}
