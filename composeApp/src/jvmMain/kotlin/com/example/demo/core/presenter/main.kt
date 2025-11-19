package com.example.demo.core.presenter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.demo.core.presenter.di.DI

fun main() = application {
    // Start Koin for DI
    DI.initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "demo",
    ) {
        App()
    }
}