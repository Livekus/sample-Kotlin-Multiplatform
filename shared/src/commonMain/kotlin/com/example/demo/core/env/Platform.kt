package com.example.demo.core.env

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform