package com.example.demo.core.data

import com.example.demo.core.domain.User
import com.example.demo.core.domain.UserRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Frontend repository abstraction for Users using Flow.
 */
interface UserRepository {
    fun getUsers(): Flow<List<User>>
    fun getUser(id: Long): Flow<User?>
    fun createUser(request: UserRequest): Flow<User>
    fun updateUser(id: Long, request: UserRequest): Flow<User?>
    fun deleteUser(id: Long): Flow<Boolean>
}

class UserRepositoryImpl(
    private val service: UserService
) : UserRepository {
    override fun getUsers(): Flow<List<User>> = flow { emit(service.getAll()) }
    override fun getUser(id: Long): Flow<User?> = flow { emit(service.getById(id)) }
    override fun createUser(request: UserRequest): Flow<User> = flow { emit(service.create(request)) }
    override fun updateUser(id: Long, request: UserRequest): Flow<User?> = flow { emit(service.update(id, request)) }
    override fun deleteUser(id: Long): Flow<Boolean> = flow { emit(service.delete(id)) }
}
