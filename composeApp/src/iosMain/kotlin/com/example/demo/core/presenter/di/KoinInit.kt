package com.example.demo.core.presenter.di

import com.example.demo.core.presenter.viewmodel.AppViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

actual object DI {
    private var started = false

    actual fun initKoin() {
        if (started) return
        startKoin {
            modules(appModule)
        }
        started = true
    }

    actual fun getAppViewModelOrNull(): AppViewModel? =
        runCatching { object : KoinComponent {}.get<AppViewModel>() }.getOrNull()
}
