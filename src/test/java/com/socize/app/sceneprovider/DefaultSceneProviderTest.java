package com.socize.app.sceneprovider;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.appscenes.AppScenes;
import com.socize.app.sceneprovider.dto.SceneResult;
import com.socize.app.sceneprovider.scenefactory.SceneFactory;
import com.socize.pages.PageController;

class DefaultSceneProviderTest {
    
    @Test
    void shouldCacheAndReturnSameSceneresult_IfParentIsRequestedMultipleTimes() {
        AppScenes mockScene = mock(AppScenes.class);
        SceneFactory mockFactory = mock(SceneFactory.class);

        doReturn
        (
            new SceneResult<PageController>(null, null), 
            new SceneResult<PageController>(null, null)
        )
        .when(mockFactory)
        .load(mockScene);

        DefaultSceneProvider provider = DefaultSceneProvider.createTestingProvider(mockFactory);

        SceneResult<PageController> first = provider.getScene(mockScene);
        SceneResult<PageController> second = provider.getScene(mockScene);

        assertSame(first, second);
    }
}
