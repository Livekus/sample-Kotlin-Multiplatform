package com.example.demo.core.env

actual object Env {
    // Use Android emulator loopback by default so the app can reach local server
    actual val baseUrl: String = "http://10.0.2.2:$SERVER_PORT"
}
