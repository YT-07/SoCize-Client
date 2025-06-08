package com.socize.utilities;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FileSizeTrackerTest {
    
    @Test
    void shouldThrowIllegalArgumentException_IfMinFileSizeIsLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FileSizeTracker(-1);
        });
    }
}
