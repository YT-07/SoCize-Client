package com.socize.shared.mainmenupagestate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.socize.app.sceneloader.AppScene;
import com.socize.shared.mainmenupagestate.spi.MainMenuPageState;

public class DefaultMainMenuPageState implements MainMenuPageState {
    private static DefaultMainMenuPageState instance;

    private AppScene scene;
    private final List<Consumer<AppScene>> observers;

    private DefaultMainMenuPageState() {
        observers = new ArrayList<>();
    }

    public static DefaultMainMenuPageState getInstance() {
        if(instance == null) {
            instance = new DefaultMainMenuPageState();
        }

        return instance;
    }

    @Override
    public void subscribe(Consumer<AppScene> observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Consumer<AppScene> observer) {
        observers.remove(observer);
    }

    @Override
    public void setPage(AppScene scene) {
        
        synchronized(scene) {
            this.scene = scene;
            notifyObservers();
        }
    }
    
    private void notifyObservers() {

        for(Consumer<AppScene> observer : observers) {
            observer.accept(scene);
        }
    }
}
