package com.example.demo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.demo.env.Env
import com.example.demo.user.HttpUserService
import com.example.demo.user.UserRepositoryImpl
import com.example.demo.user.UserUsecase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

/**
 * Simple ViewModel used by App composable in commonMain.
 * Holds UI state and exposes actions.
 */
class AppViewModel(
    private val greetingProvider: Greeting,
    private val userUsecase: UserUsecase
) : ViewModel() {
    // UI state
    var showContent by mutableStateOf(false)
        private set

    // Expensive greeting can be computed once and retained in VM
    val greeting: String by lazy { greetingProvider.greet() }

    fun onToggleClick() {
        showContent = !showContent
    }

    // Convenience no-arg constructor for environments without DI
    constructor() : this(
        Greeting(),
        defaultUserUsecase()
    )

    companion object {
        private fun defaultUserUsecase(): UserUsecase {
            val client = HttpClient {
                install(ContentNegotiation) { json() }
            }
            val baseUrl = Env.baseUrl
            val service = HttpUserService(client, baseUrl)
            return UserUsecase(UserRepositoryImpl(service))
        }
    }
}
