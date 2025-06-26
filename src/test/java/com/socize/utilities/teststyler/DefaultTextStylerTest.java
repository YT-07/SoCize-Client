package com.socize.utilities.teststyler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.socize.utilities.textstyler.DefaultTextStyler;

public class DefaultTextStylerTest {

    @Test
    void shouldReturnSameInstance_WhenGettingInstanceMultipleTimes() {
        DefaultTextStyler textStyler1 = DefaultTextStyler.getInstance();
        DefaultTextStyler textStyler2 = DefaultTextStyler.getInstance();
        
        assertEquals(textStyler1, textStyler2);
    }
    
}
