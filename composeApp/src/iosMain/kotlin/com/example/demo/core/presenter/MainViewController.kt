package com.example.demo.core.presenter

import androidx.compose.ui.window.ComposeUIViewController
import com.example.demo.core.presenter.di.DI
import platform.UIKit.UIViewController

fun MainViewController() : UIViewController {
    // Start Koin for DI on iOS
    DI.initKoin()
    return ComposeUIViewController { App() }
}