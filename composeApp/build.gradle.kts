import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.core)
            // Ktor OkHttp engine for Android
            implementation(libs.ktorClientOkhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            // Animations (for AnimatedVisibility, etc.)
            implementation(compose.animation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)
            // Ktor client for calling server REST API
            implementation(libs.ktorClientCore)
            implementation(libs.ktorClientContentNegotiation)
            implementation(libs.ktorSerializationKotlinxJsonClient)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            // Coroutines core for runBlocking/withTimeout in common tests
            implementation(libs.kotlinx.coroutines.core)
            // Coroutines test utilities for multiplatform runTest
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.koin.core)
            // Ktor OkHttp engine for Desktop/JVM
            implementation(libs.ktorClientOkhttp)
            // SLF4J binding to remove "no providers" warning and enable logging
            runtimeOnly(libs.logback)
        }
        iosMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.ktorClientDarwin)
        }
        jsMain.dependencies {
            // Ktor JS engine so HttpClient works on web when used
            implementation(libs.ktorClientJs)
        }
    }
}

android {
    namespace = "com.example.demo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    buildFeatures {
        // Required to generate BuildConfig when using custom buildConfigField entries
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.demo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        // Fallback BASE_URL; flavors can override
        buildConfigField("String", "BASE_URL", "\"http://localhost:8080\"")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.example.demo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.example.demo"
            packageVersion = "1.0.0"
        }
    }
}
