package com.socize.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to provide common method for file I/O operations.
 */
public class FileIO {
    private static final Logger logger = LoggerFactory.getLogger(FileIO.class);

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
                    logger.info("File '{}' successfully deleted.", filePath.toString());

                } catch (Exception e) {
                    logger.error("Failed to delete file '{}'.", filePath.toString(), e);
                }
            }
            
        };

        Files.createFile(filePath); // If exception occur here, will not register rollback function to stack
        logger.info("File '{}' successfully created.", filePath.toString());
        
        if(rollbackStack != null) {
            rollbackStack.push(rollbackCreateFileTask);
            logger.info("Successfully registered rollback task to delete file '{}'.", filePath.toString());
        }
    }
}
