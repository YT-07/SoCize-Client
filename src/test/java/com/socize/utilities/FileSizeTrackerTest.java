package com.socize.utilities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FileSizeTrackerTest {
    
    @Test
    void shouldThrowIllegalArgumentException_IfMinFileSizeIsLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FileSizeTracker(-1);
        });
    }

    @Test
    void shouldReachMinFileSize_IfIncrementValueIsEqualToOrMoreThanMinFileSize() {
        FileSizeTracker tracker1 = new FileSizeTracker(100);
        FileSizeTracker tracker2 = new FileSizeTracker(100);

        tracker1.increment(100);
        tracker2.increment(200);

        assertAll(
            () -> {
                assertTrue(tracker1.hasReachedMinFileSize());
            },

            () -> {
                assertTrue(tracker2.hasReachedMinFileSize());
            }
        );
        
    }

    @Test
    void shouldNotIntegerOverflow_IfIncrementMoreThanIntegerMaxValue() {
        FileSizeTracker tracker = new FileSizeTracker(Long.MAX_VALUE);

        tracker.increment(Long.MAX_VALUE);
        tracker.increment(1);

        assertTrue(tracker.hasReachedMinFileSize());
    }
}
