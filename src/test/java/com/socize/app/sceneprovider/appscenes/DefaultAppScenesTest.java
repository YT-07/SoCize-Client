package com.socize.app.sceneprovider.appscenes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DefaultAppScenesTest {

    @Test
    void sceneFilePathShouldBeValidPaths() {
        List<String> failedAssertions = new ArrayList<>();

        for(DefaultAppScenes scene : DefaultAppScenes.values()) {

            try {

                Paths.get(scene.getPath());

            } catch (InvalidPathException e) {
                failedAssertions.add(scene.name());
            }

        }

        assertTrue(failedAssertions.isEmpty(), "App scene for '" + String.join(", ", failedAssertions) + "' does not contain valid file paths.");
    }

    @Test
    void sceneControllerFactoryForAppSceneShouldNotBeNull() {
        List<String> faliedAssertions = new ArrayList<>();

        for(DefaultAppScenes scene : DefaultAppScenes.values()) {

            if(scene.getControllerFactory() == null) {
                faliedAssertions.add(scene.name());
            }

        }

        assertTrue(faliedAssertions.isEmpty(), "Scene controller factory for app scene '" + String.join(", ", faliedAssertions) + "is null.");
    }
}
