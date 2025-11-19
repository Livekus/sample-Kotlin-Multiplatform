package com.example.demo.core.presenter.viewmodel

import com.example.demo.core.data.UserRepository
import com.example.demo.core.domain.User
import com.example.demo.core.domain.UserRequest
import com.example.demo.core.env.Greeting
import com.example.demo.core.presenter.usecase.UserUsecase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppViewModelTest {

    private class FakeUserRepository(
        private val users: List<User>
    ) : UserRepository {
        override fun getUsers(): Flow<List<User>> = flowOf(users)
        override fun getUser(id: Long): Flow<User?> = flowOf(users.find { it.id == id })
        override fun createUser(request: UserRequest): Flow<User> = flowOf(
            User(id = 999, name = request.name, email = request.email)
        )
        override fun updateUser(id: Long, request: UserRequest): Flow<User?> = flowOf(
            users.find { it.id == id }?.copy(name = request.name, email = request.email)
        )
        override fun deleteUser(id: Long): Flow<Boolean> = flowOf(users.any { it.id == id })
    }

    @Test
    fun onToggleClick_updatesShowContent_andAppendsFirstUserName() = runTest {
        val fakeRepo = FakeUserRepository(
            listOf(User(id = 1, name = "Alice", email = "alice@example.com"))
        )
        val vm = AppViewModel(
            greetingProvider = Greeting(),
            userUsecase = UserUsecase(fakeRepo)
        )

        // Initial state
        assertFalse(vm.showContent, "showContent should be false initially")
        assertTrue(vm.greeting.startsWith("Hello,"), "Greeting should start with 'Hello,'")

        // Act
        vm.onToggleClick()

        // showContent toggles immediately
        assertTrue(vm.showContent, "showContent should toggle to true after click")

        // Greeting update happens asynchronously; wait until it contains the user name
        withTimeout(2_000) {
            while (!vm.greeting.contains("Alice")) {
                delay(10)
            }
        }
        assertTrue(vm.greeting.contains("Alice"), "Greeting should include first user's name after loading")
    }
}
