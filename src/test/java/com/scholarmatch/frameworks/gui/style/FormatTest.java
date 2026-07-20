package com.scholarmatch.frameworks.gui.style;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatTest {

    @Test
    void testStatReturnsNAWhenValueIsNull() {
        assertEquals("N/A", Format.stat(null));
    }

    @Test
    void testStatReturnsStringValueWhenNonNull() {
        assertEquals("42", Format.stat(42));
    }

    @Test
    void testStatHandlesZero() {
        assertEquals("0", Format.stat(0));
    }
}
