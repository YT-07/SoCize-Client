package com.socize.pages.mainmenu.shared.mainmenupagestate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import com.socize.app.sceneprovider.appscenes.AppScenes;

public class DefaultMainMenuPageStateTest {
    
    @Test
    void shouldNotifyObservers_WhenPageStateChange() {
        DefaultMainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();

        @SuppressWarnings("unchecked")
        Consumer<AppScenes> mockObserver = mock(Consumer.class);

        mainMenuPageState.subscribe(mockObserver);

        AppScenes mockScene = mock(AppScenes.class);
        mainMenuPageState.setPage(mockScene);

        verify(mockObserver, times(1)).accept(mockScene);
    }

    @Test
    void shouldNotNotifyUnsubscribedObservers() {
        DefaultMainMenuPageState mainMenuPageState = DefaultMainMenuPageState.getInstance();

        @SuppressWarnings("unchecked")
        Consumer<AppScenes> mockObserver = mock(Consumer.class);

        mainMenuPageState.subscribe(mockObserver);

        AppScenes mockScene = mock(AppScenes.class);
        mainMenuPageState.unsubscribe(mockObserver);
        mainMenuPageState.setPage(mockScene);

        verify(mockObserver, times(0)).accept(mockScene);
    }
}
