package com.example.demo.feature.style

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FeatureStyleViewModelTest {

    private val vm = FeatureStyleViewModel()

    @AfterTest
    fun tearDown() {
        StyleController.applyAltStyle(false)
    }

    @Test
    fun altStyle_reflectsController_defaultFalse() {
        assertFalse(vm.altStyle, "Default altStyle should be false")
    }

    @Test
    fun toggleStyle_togglesControllerState() {
        val initial = vm.altStyle
        vm.toggleStyle()
        assertEquals(!initial, vm.altStyle)
        vm.toggleStyle()
        assertEquals(initial, vm.altStyle)
    }

    @Test
    fun controllerChange_reflectedInViewModelGetter() {
        StyleController.applyAltStyle(true)
        assertTrue(vm.altStyle)
        StyleController.applyAltStyle(false)
        assertFalse(vm.altStyle)
    }
}
