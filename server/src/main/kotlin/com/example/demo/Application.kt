package com.example.demo

import com.example.demo.core.env.Greeting
import com.example.demo.core.env.SERVER_PORT
import com.example.demo.repository.UserRepository
import com.example.demo.routes.userRoutes
import com.example.demo.di.appModule
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin
import org.koin.ktor.ext.inject

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(appModule)
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }

    val userRepository by inject<UserRepository>()
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }

        userRoutes(userRepository)
    }
}