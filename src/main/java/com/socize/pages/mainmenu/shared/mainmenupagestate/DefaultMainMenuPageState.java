package com.socize.pages.mainmenu.shared.mainmenupagestate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.socize.app.sceneprovider.appscenes.AppScenes;

public class DefaultMainMenuPageState implements MainMenuPageState {
    private AppScenes scene;
    private final List<Consumer<AppScenes>> observers;

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
    public void subscribe(Consumer<AppScenes> observer) {
        
        synchronized(observers) {
            observers.add(observer);
        }

    }

    @Override
    public void unsubscribe(Consumer<AppScenes> observer) {
        
        synchronized(observers) {
            observers.remove(observer);
        }

    }

    @Override
    public synchronized void setPage(AppScenes scene) {
        this.scene = scene;
        notifyObservers();
    }
    
    private void notifyObservers() {

        synchronized(observers) {
            for(Consumer<AppScenes> observer : observers) {
                observer.accept(scene);
            }
        }
        
    }
}
