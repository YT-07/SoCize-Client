package com.socize.utilities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileIOTest {
    
    @TempDir
    private Path tempDir;

    @Test
    void shouldCreateFile_IfFilePathProvidedIsValid() throws IOException {
        Path path = Paths.get(tempDir.toString(), "test.txt");
        FileIO.createFileAtomic(path, null);

        assertTrue(path.toFile().exists());
    }

    @Test
    void shouldRegisterRollbackFunction_IfRollbackStackIsProvided() throws IOException {
        Path path = Paths.get(tempDir.toString(), "test.txt");
        Stack<Runnable> stack = new Stack<>();

        FileIO.createFileAtomic(path, stack);

        assertFalse(stack.isEmpty());
    }

    @Test
    void shouldRemoveCreatedFile_IfRollbackFunctionIsExecuted() throws IOException {
        Path path = Paths.get(tempDir.toString(), "test.txt");
        Stack<Runnable> stack = new Stack<>();

        FileIO.createFileAtomic(path, stack);
        assertTrue(path.toFile().exists());

        stack.pop().run();
        assertFalse(path.toFile().exists());
    }
}
