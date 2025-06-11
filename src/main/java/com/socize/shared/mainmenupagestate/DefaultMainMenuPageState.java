package com.socize.shared.mainmenupagestate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.socize.app.sceneprovider.AppScene;

public class DefaultMainMenuPageState implements MainMenuPageState {
    private AppScene scene;
    private final List<Consumer<AppScene>> observers;

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
    public void subscribe(Consumer<AppScene> observer) {
        
        synchronized(observers) {
            observers.add(observer);
        }

    }

    @Override
    public void unsubscribe(Consumer<AppScene> observer) {
        
        synchronized(observers) {
            observers.remove(observer);
        }

    }

    @Override
    public synchronized void setPage(AppScene scene) {
        this.scene = scene;
        notifyObservers();
    }
    
    private void notifyObservers() {

        synchronized(observers) {
            for(Consumer<AppScene> observer : observers) {
                observer.accept(scene);
            }
        }
        
    }
}
