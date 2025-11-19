package com.example.demo.core.presenter.env

import com.example.demo.BuildConfig

// Note: Shared module already declares expect/actual Env.
// This Android-specific file in composeApp must not provide an actual.
// Keep a plain object for local reference if needed by Android UI code.
object PresenterEnvConfig {
    val baseUrl: String = BuildConfig.BASE_URL
}
