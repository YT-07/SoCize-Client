package com.socize.app.sceneprovider.appscenes;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;

public interface AppScenes {

    SceneControllerFactory getControllerFactory();
    String getPath();
}