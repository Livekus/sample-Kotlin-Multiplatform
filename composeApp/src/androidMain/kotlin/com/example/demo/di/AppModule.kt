package com.example.demo.di

import com.example.demo.AppViewModel
import com.example.demo.Greeting
import com.example.demo.env.Env
import com.example.demo.user.HttpUserService
import com.example.demo.user.UserRepository
import com.example.demo.user.UserRepositoryImpl
import com.example.demo.user.UserService
import com.example.demo.user.UserUsecase
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
