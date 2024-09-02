package com.andruszkiewiczarturmobileeng.planyourprotocol

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.andruszkiewiczarturmobileeng.planyourprotocol.controller.snackbar.SnackbarController
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.compose.KeyboardAware
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.HomePresentation
import com.example.compose.darkColorScheme
import com.example.compose.lightColorScheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    MaterialTheme(
        colorScheme = if(isSystemInDarkTheme()) darkColorScheme else lightColorScheme
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()

                val result = snackbarHostState.showSnackbar(
                    message = event.message,
                    actionLabel = event.action?.name,
                    duration = SnackbarDuration.Long
                )

                if(result == SnackbarResult.ActionPerformed) {
                    event.action?.action?.invoke()
                }
            }
        }

        KeyboardAware {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState
                    )
                }
            ) {
                HomePresentation()
            }
        }
    }
}