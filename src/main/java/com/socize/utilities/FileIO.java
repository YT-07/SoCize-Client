package com.socize.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

/**
 * Class to provide common method for file I/O operations.
 */
public class FileIO {
    
    private FileIO() {
        throw new UnsupportedOperationException("Utility class not meant to be instantiated.");
    }

    /**
     * Creates a file at {@code filePath} and registers a rollback function for this operation in {@code rollbackStack}
     * 
     * @param filePath the file path of the new file to create
     * @param rollbackStack the stack to register a rollback function for this operation, or {@code null} if registration of rollback function is not required
     * @throws IOException if any error occurs during file operation
     */
    public static void createFileAtomic(Path filePath, Stack<Runnable> rollbackStack) throws IOException {

        Runnable rollbackCreateFileTask = new Runnable() {

            @Override
            public void run() {

                try {

                    Files.deleteIfExists(filePath);

                } catch (Exception e) {
                    // TODO: Log error if fail to delete file
                }
            }
            
        };

        Files.createFile(filePath); // If exception occur here, will not register rollback function to stack
        
        if(rollbackStack != null) {
            rollbackStack.push(rollbackCreateFileTask);
        }
    }
}
