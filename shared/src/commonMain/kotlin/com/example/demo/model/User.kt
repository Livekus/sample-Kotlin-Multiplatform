package com.example.demo.model

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
