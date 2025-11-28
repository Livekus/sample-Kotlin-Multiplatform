package com.example.demo.core.presenter.di

import com.example.demo.core.presenter.viewmodel.AppViewModel
import com.example.demo.core.presenter.usecase.UserUsecase

// Multiplatform declaration; platform-specific actual implementations live in
// androidMain, jvmMain, iosMain, and webMain.
expect object DI {
    fun initKoin()
    // Try to resolve AppViewModel from DI on platforms that support Koin.
    // Returns null if DI is not available on the current platform or not started.
    fun getAppViewModelOrNull(): AppViewModel?
    // Non-null accessor that must return a working instance of UserUsecase on all platforms.
    // On platforms without DI, it should construct a default implementation.
    fun getUserUsecase(): UserUsecase
}
