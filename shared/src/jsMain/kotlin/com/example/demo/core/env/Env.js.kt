package com.example.demo.core.env

import kotlinx.browser.window

actual object Env {
    // Use current origin when running in browser, fallback to localhost:8080
    actual val baseUrl: String = (window.location.origin.ifEmpty { "http://127.0.0.1:8080" })
}
