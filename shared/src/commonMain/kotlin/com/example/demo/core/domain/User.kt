package com.example.demo.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val name: String,
    val email: String
)

@Serializable
data class UserRequest(
    val name: String,
    val email: String
)
