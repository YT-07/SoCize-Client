package com.socize.app.sceneprovider;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.app.sceneprovider.scenefactory.SceneFactory;
import com.socize.pages.TransitionablePage;

class DefaultSceneProviderTest {
    
    @Test
    void shouldCacheAndReturnSameSceneresult_IfParentIsRequestedMultipleTimes() {
        DefaultAppScenes mockScene = DefaultAppScenes.MAIN_PAGE;
        
        SceneFactory mockFactory = mock(SceneFactory.class);

        doReturn
        (
            new SceneResult<TransitionablePage>(null, null), 
            new SceneResult<TransitionablePage>(null, null)
        )
        .when(mockFactory)
        .load(mockScene);

        DefaultSceneProvider provider = DefaultSceneProvider.createTestingProvider(mockFactory);

        SceneResult<TransitionablePage> first = provider.getScene(mockScene);
        SceneResult<TransitionablePage> second = provider.getScene(mockScene);

        assertSame(first, second);
    }
}
