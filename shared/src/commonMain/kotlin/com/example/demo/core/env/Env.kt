package com.example.demo.core.env

/**
 * Environment configuration shared API.
 * Each platform provides its own actual implementation.
 */
expect object Env {
    val baseUrl: String
}
