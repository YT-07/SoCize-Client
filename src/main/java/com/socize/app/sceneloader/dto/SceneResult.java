package com.socize.app.sceneloader.dto;

import com.socize.pages.TransitionablePage;

import javafx.scene.Parent;

public record SceneResult<T extends TransitionablePage>
(
    Parent parent,
    T controller
) 
{}