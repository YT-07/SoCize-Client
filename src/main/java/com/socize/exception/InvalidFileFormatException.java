package com.socize.exception;

/**
 * Signals that the file format is invalid or corrupted.
 */
public class InvalidFileFormatException extends Exception {
    
    public InvalidFileFormatException(String message) {
        super(message);
    }
}
