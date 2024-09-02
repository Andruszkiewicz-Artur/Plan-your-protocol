package com.andruszkiewiczarturmobileeng.planyourprotocol

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.andruszkiewiczarturmobileeng.planyourprotocol.controller.snackbar.SnackbarController
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.Static
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.compose.KeyboardAware
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.compose.ObserveAsEvents
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ThemType
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.HomePresentation
import com.example.compose.darkColorScheme
import com.example.compose.lightColorScheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>
) {
    val them by prefs.data.map { dataStore ->
        val themKey = stringPreferencesKey(Static.THEM_PREFS)
        ThemType.valueOf(dataStore[themKey] ?: "System")
    }.collectAsState(ThemType.System)

    MaterialTheme(
        colorScheme = when(them) {
            ThemType.System -> if(isSystemInDarkTheme()) darkColorScheme else lightColorScheme
            ThemType.Light -> lightColorScheme
            ThemType.Dark -> darkColorScheme
        }
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
                HomePresentation(prefs)
            }
        }
    }
}