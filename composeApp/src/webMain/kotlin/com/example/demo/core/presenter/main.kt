package com.example.demo.core.presenter

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.example.demo.core.presenter.di.DI

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Start Koin for DI on web
    DI.initKoin()
    ComposeViewport {
        App()
    }
}