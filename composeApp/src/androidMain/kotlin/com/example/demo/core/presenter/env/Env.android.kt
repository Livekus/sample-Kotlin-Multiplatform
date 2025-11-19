package com.example.demo.core.presenter.env

import com.example.demo.BuildConfig

actual object Env {
    actual val baseUrl: String = BuildConfig.BASE_URL
}
