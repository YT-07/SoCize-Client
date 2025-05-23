package com.socize.utilities;

/**
 * Offer services to track a file's size and 
 * check if the file's size has reached it's 
 * intended minumum value.
 */
public class FileSizeTracker {
    private final long minFileSize;
    private long totalFileSize;
    private boolean reachedMinFileSize;

    /**
     * Constructs a file size tracker with a specified minimum file size.
     * 
     * @param minFileSize the minimum file size, must be 0 or more.
     * @throws IllegalArgumentException if {@code minFileSize} is less than 0
     */
    public FileSizeTracker(long minFileSize) {
        
        if(minFileSize < 0) {
            throw new IllegalArgumentException("File size must be 0 or more.");
        }

        this.minFileSize = minFileSize;
        totalFileSize = 0;
        reachedMinFileSize = false;
    }

    /**
     * Increments the file size that's being tracked by this instance. 
     * This method can only track up to a maximum number of {@code Long.MAX_VALUE} 
     * and will stop incrementing once this treshold is reached to prevent integer overflow.
     * 
     * @param bytesRead the amount of bytes read during a file operation
     */
    public void increment(long bytesRead) {

        if(reachedMinFileSize) {
            return;
        }

        // Find the max value to increment for a data type to
        // prevent integer overflow
        long maxIncrementValueAllowed = Long.MAX_VALUE - totalFileSize;

        if(bytesRead <= maxIncrementValueAllowed) {
            totalFileSize += bytesRead;

        } else {
            totalFileSize += maxIncrementValueAllowed; // To prevent integer overflow

        }

        updateReachedMinFileSizeStatus();
    }

    /**
     * Checks if the file size being tracked has already reached 
     * the minimum file size that was specified.
     * 
     * @return {@code true} if the file size is equals or more than the mininum file size that's specified, else {@code false}
     */
    public boolean hasReachedMinFileSize() {
        return reachedMinFileSize;
    }

    /**
     * Gets the file size that's being tracked.
     * 
     * @return the total file size
     */
    public long getTotalFileSize() {
        return totalFileSize;
    }

    /**
     * Updates the current status of this instance to check 
     * if the file size had already reached the minumum 
     * file size specified.
     */
    private void updateReachedMinFileSizeStatus() {

        if(totalFileSize >= minFileSize) {
            reachedMinFileSize = true;

        } else {
            reachedMinFileSize = false;
        }

    }
}
