package com.socize.app.sceneprovider.scenecontrollerfactory.impl;

import com.socize.app.sceneprovider.scenecontrollerfactory.spi.SceneControllerFactory;
import com.socize.pages.PageController;
import com.socize.pages.fileserver.shared.session.DefaultSessionManager;
import com.socize.pages.fileserver.shared.session.SessionManager;
import com.socize.pages.fileserver.user.UserController;

public class DefaultUserControllerFactory implements SceneControllerFactory {

    @Override
    public PageController createDefault() {
        SessionManager sessionManager = DefaultSessionManager.getInstance();

        return new UserController(sessionManager);
    }
    
}
