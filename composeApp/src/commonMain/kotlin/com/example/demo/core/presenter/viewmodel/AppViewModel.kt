package com.example.demo.core.presenter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.demo.core.data.HttpUserService
import com.example.demo.core.data.UserRepositoryImpl
import com.example.demo.core.env.Env
import com.example.demo.core.env.Greeting
import com.example.demo.core.presenter.usecase.UserUsecase
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

    // Greeting text displayed in UI; initialized with default greeting
    var greeting: String by mutableStateOf(greetingProvider.greet())
        private set

    // Local coroutine scope for commonMain (don't rely on platform-specific viewModelScope)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun onToggleClick() {
        // Toggle visibility immediately
        showContent = !showContent
        // Fetch users and update greeting to first user's name
        scope.launch {
            runCatching {
                val users = userUsecase.listUsers().first()
                val firstName = users.firstOrNull()?.name
                if (firstName != null && !greeting.contains(firstName)){
                    greeting = "$greeting user : $firstName"
                }
            }.onFailure {
                // Keep existing greeting on failure; optionally could set an error state
            }
        }
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

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}