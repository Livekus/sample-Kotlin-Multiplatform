package com.example.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.demo.di.DI
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import demo.composeapp.generated.resources.Res
import demo.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        val vm: AppViewModel = run {
            // Prefer DI-provided ViewModel when available (e.g., Desktop/JVM, Android, iOS)
            val injected = remember { DI.getAppViewModelOrNull() }
            if (injected != null) {
                injected
            } else {
                val owner = LocalViewModelStoreOwner.current
                if (owner != null) {
                    // Platforms with lifecycle owner (e.g., Android) – use androidx ViewModel
                    viewModel<AppViewModel>()
                } else {
                    // No DI and no owner (e.g., web/preview) – last resort local instance
                    remember { AppViewModel() }
                }
            }
        }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { vm.onToggleClick() }) {
                Text("Click me!")
            }
            AnimatedVisibility(vm.showContent) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: ${vm.greeting}")
                }
            }
        }
    }
}