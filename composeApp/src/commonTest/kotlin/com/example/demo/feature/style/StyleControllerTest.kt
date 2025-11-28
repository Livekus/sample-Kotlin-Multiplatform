package com.example.demo.feature.style

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StyleControllerTest {

    @AfterTest
    fun tearDown() {
        // Ensure singleton state does not leak between tests
        StyleController.applyAltStyle(false)
    }

    @Test
    fun default_isFalse() {
        assertFalse(StyleController.altStyle, "altStyle should be false by default")
    }

    @Test
    fun toggle_invertsState() {
        // false -> true
        StyleController.toggle()
        assertTrue(StyleController.altStyle)
        // true -> false
        StyleController.toggle()
        assertFalse(StyleController.altStyle)
    }

    @Test
    fun applyAltStyle_setsExplicitValue() {
        StyleController.applyAltStyle(true)
        assertTrue(StyleController.altStyle)
        StyleController.applyAltStyle(false)
        assertFalse(StyleController.altStyle)
    }
}
