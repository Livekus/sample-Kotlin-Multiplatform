package com.example.demo.core.presenter.viewmodel

import com.example.demo.core.env.Greeting
import com.example.demo.core.presenter.usecase.UserUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AppViewModelFailureTest {
    @Test
    fun onToggleClick_failure_keepsGreetingAndTogglesVisibility() = runTest {
        val failingRepo = object : com.example.demo.core.data.UserRepository {
            override fun getUsers(): Flow<List<com.example.demo.core.domain.User>> = flow {
                throw RuntimeException("boom")
            }
            override fun getUser(id: Long): Flow<com.example.demo.core.domain.User?> = flow { emit(null) }
            override fun createUser(request: com.example.demo.core.domain.UserRequest): Flow<com.example.demo.core.domain.User> = flow { throw RuntimeException("boom") }
            override fun updateUser(id: Long, request: com.example.demo.core.domain.UserRequest): Flow<com.example.demo.core.domain.User?> = flow { throw RuntimeException("boom") }
            override fun deleteUser(id: Long): Flow<Boolean> = flow { throw RuntimeException("boom") }
        }

        val vm = AppViewModel(greetingProvider = Greeting(), userUsecase = UserUsecase(failingRepo))

        val initialGreeting = vm.greeting
        // Act: this will attempt to load users and fail
        vm.onToggleClick()

        // showContent toggles immediately even if background fails
        assertTrue(vm.showContent)
        // Greeting remains unchanged on failure path
        // Give the background coroutine a tiny bit of time
        kotlinx.coroutines.delay(50)
        assertEquals(initialGreeting, vm.greeting)
    }
}
