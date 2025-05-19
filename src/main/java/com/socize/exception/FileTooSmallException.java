package com.socize.exception;

/**
 * Signals that a file's size did not meet the minimum file size requirement.
 */
public class FileTooSmallException extends Exception {
    
    public FileTooSmallException(String message) {
        super(message);
    }
}
