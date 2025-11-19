package com.example.demo.core.presenter.usecase

import com.example.demo.core.data.UserRepository
import com.example.demo.core.domain.User
import com.example.demo.core.domain.UserRequest
import kotlinx.coroutines.flow.Flow

/**
 * Application use cases for User feature using Flow.
 */
class UserUsecase(
    private val repository: UserRepository
) {
    fun listUsers(): Flow<List<User>> = repository.getUsers()
    fun getUser(id: Long): Flow<User?> = repository.getUser(id)
    fun createUser(name: String, email: String): Flow<User> =
        repository.createUser(UserRequest(name = name, email = email))

    fun updateUser(id: Long, name: String, email: String): Flow<User?> =
        repository.updateUser(id, UserRequest(name = name, email = email))

    fun deleteUser(id: Long): Flow<Boolean> = repository.deleteUser(id)
}