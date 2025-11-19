package com.example.demo.core.presenter.di

import com.example.demo.core.presenter.viewmodel.AppViewModel

// Multiplatform declaration; platform-specific actual implementations live in
// androidMain, jvmMain, iosMain, and webMain.
expect object DI {
    fun initKoin()
    // Try to resolve AppViewModel from DI on platforms that support Koin.
    // Returns null if DI is not available on the current platform or not started.
    fun getAppViewModelOrNull(): AppViewModel?
}
