package com.socize.app.sceneprovider.dto;

import com.socize.pages.PageController;

import javafx.scene.Parent;

public record SceneResult<T extends PageController>
(
    Parent parent,
    T controller
) 
{}