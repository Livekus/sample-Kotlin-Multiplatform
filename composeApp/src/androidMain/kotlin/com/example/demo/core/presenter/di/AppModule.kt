package com.example.demo.core.presenter.di

import com.example.demo.core.presenter.viewmodel.AppViewModel
import com.example.demo.core.env.Greeting
import com.example.demo.core.env.Env
import com.example.demo.core.data.HttpUserService
import com.example.demo.core.data.UserRepository
import com.example.demo.core.data.UserRepositoryImpl
import com.example.demo.core.data.UserService
import com.example.demo.core.presenter.usecase.UserUsecase
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

val appModule = module {
    single { Greeting() }
    // User feature DI graph
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) { json() }
        }
    }
    single<UserService> {
        val client: HttpClient = get()
        HttpUserService(client, baseUrl = Env.baseUrl)
    }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single { UserUsecase(get()) }

    factory { AppViewModel(get(), get()) }
}
