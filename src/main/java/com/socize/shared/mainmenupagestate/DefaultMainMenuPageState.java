package com.socize.shared.mainmenupagestate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.socize.app.sceneprovider.appscenes.DefaultAppScenes;

public class DefaultMainMenuPageState implements MainMenuPageState {
    private DefaultAppScenes scene;
    private final List<Consumer<DefaultAppScenes>> observers;

    private DefaultMainMenuPageState() {
        observers = new ArrayList<>();
    }

    private static class SingletonInstanceHolder {
        private static final DefaultMainMenuPageState INSTANCE = new DefaultMainMenuPageState();
    }

    public static DefaultMainMenuPageState getInstance() {
        return SingletonInstanceHolder.INSTANCE;
    }

    @Override
    public void subscribe(Consumer<DefaultAppScenes> observer) {
        
        synchronized(observers) {
            observers.add(observer);
        }

    }

    @Override
    public void unsubscribe(Consumer<DefaultAppScenes> observer) {
        
        synchronized(observers) {
            observers.remove(observer);
        }

    }

    @Override
    public synchronized void setPage(DefaultAppScenes scene) {
        this.scene = scene;
        notifyObservers();
    }
    
    private void notifyObservers() {

        synchronized(observers) {
            for(Consumer<DefaultAppScenes> observer : observers) {
                observer.accept(scene);
            }
        }
        
    }
}
