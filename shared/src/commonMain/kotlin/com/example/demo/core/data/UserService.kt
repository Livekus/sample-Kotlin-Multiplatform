package com.example.demo.core.data

import com.example.demo.core.domain.User
import com.example.demo.core.domain.UserRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Abstraction over the server REST API for User resources.
 */
interface UserService {
    suspend fun getAll(): List<User>
    suspend fun getById(id: Long): User?
    suspend fun create(request: UserRequest): User
    suspend fun update(id: Long, request: UserRequest): User?
    suspend fun delete(id: Long): Boolean
}

/**
 * Real HTTP implementation using Ktor Client against the server REST API.
 */
class HttpUserService(
    private val client: HttpClient,
    private val baseUrl: String
) : UserService {

    override suspend fun getAll(): List<User> =
        client.get("$baseUrl/users").body()

    override suspend fun getById(id: Long): User? =
        runCatching { client.get("$baseUrl/users/$id").body<User>() }.getOrNull()

    override suspend fun create(request: UserRequest): User =
        client.post("$baseUrl/users") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    override suspend fun update(id: Long, request: UserRequest): User? =
        runCatching {
            client.put("$baseUrl/users/$id") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<User>()
        }.getOrNull()

    override suspend fun delete(id: Long): Boolean =
        runCatching { client.delete("$baseUrl/users/$id") }.isSuccess
}
