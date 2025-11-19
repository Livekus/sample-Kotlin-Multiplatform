package com.example.demo.core.presenter.di

import com.example.demo.core.presenter.viewmodel.AppViewModel

// Web (JS + Wasm) stub DI: no Koin on web to keep dependencies wasm-compatible
actual object DI {
    actual fun initKoin() {
        // no-op on web
    }

    actual fun getAppViewModelOrNull(): AppViewModel? = null
}
