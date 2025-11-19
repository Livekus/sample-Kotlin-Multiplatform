package com.example.demo.core.env

actual object Env {
    actual val baseUrl: String = "http://localhost:$SERVER_PORT"
}
