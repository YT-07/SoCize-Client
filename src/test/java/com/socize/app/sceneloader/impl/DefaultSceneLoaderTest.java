package com.socize.app.sceneloader.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneloader.AppScene;
import com.socize.app.sceneloader.DefaultSceneLoader;
import com.socize.app.sceneloader.dto.SceneResult;
import com.socize.app.sceneloader.scenefactory.SceneFactory;
import com.socize.pages.TransitionablePage;

class DefaultSceneLoaderTest {
    
    @Test
    void shouldCacheAndReturnSameSceneresult_IfParentIsRequestedMultipleTimes() {
        AppScene mockScene = AppScene.MAIN_PAGE;
        
        SceneFactory mockFactory = mock(SceneFactory.class);

        doReturn
        (
            new SceneResult<TransitionablePage>(null, null), 
            new SceneResult<TransitionablePage>(null, null)
        )
        .when(mockFactory)
        .load(mockScene);

        DefaultSceneLoader loader = DefaultSceneLoader.createTestingLoader(mockFactory);

        SceneResult<TransitionablePage> first = loader.getScene(mockScene);
        SceneResult<TransitionablePage> second = loader.getScene(mockScene);

        assertSame(first, second);
    }
}
