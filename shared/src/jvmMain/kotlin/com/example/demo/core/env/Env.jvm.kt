package com.example.demo.core.env

actual object Env {
    // Allow override via system property: -DBASE_URL=http://host:port
    actual val baseUrl: String =
        (System.getProperty("BASE_URL")?.takeIf { it.isNotBlank() }
            ?: "http://127.0.0.1:$SERVER_PORT")
}
