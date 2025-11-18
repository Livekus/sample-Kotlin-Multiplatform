package com.example.demo.repository

import com.example.demo.model.User
import java.util.concurrent.atomic.AtomicLong

class UserRepository {

    private val idSeq = AtomicLong(0L)
    private val users = mutableMapOf<Long, User>()

    fun getAll(): List<User> = users.values.toList()

    fun getById(id: Long): User? = users[id]

    fun create(name: String, email: String): User {
        val id = idSeq.incrementAndGet()
        val user = User(id = id, name = name, email = email)
        users[id] = user
        return user
    }

    fun update(id: Long, name: String, email: String): User? {
        if (!users.containsKey(id)) return null
        val updated = User(id = id, name = name, email = email)
        users[id] = updated
        return updated
    }

    fun delete(id: Long): Boolean = users.remove(id) != null
}
