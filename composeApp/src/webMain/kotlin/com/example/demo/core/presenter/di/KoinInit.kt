package com.example.demo.core.presenter.di

import com.example.demo.core.presenter.viewmodel.AppViewModel
import com.example.demo.core.presenter.usecase.UserUsecase
import com.example.demo.core.data.HttpUserService
import com.example.demo.core.data.UserRepositoryImpl
import com.example.demo.core.env.Env
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

// Web (JS + Wasm) stub DI: no Koin on web to keep dependencies wasm-compatible
actual object DI {
    actual fun initKoin() {
        // no-op on web
    }

    actual fun getAppViewModelOrNull(): AppViewModel? = null

    // Always provide a working instance on web by constructing the dependency graph locally
    actual fun getUserUsecase(): UserUsecase {
        val client = HttpClient {
            install(ContentNegotiation) { json() }
        }
        val service = HttpUserService(client, baseUrl = Env.baseUrl)
        return UserUsecase(UserRepositoryImpl(service))
    }
}
