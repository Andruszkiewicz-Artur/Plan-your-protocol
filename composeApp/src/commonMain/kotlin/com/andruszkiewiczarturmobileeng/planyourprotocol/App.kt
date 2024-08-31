package com.andruszkiewiczarturmobileeng.planyourprotocol

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.HomePresentation
import com.example.compose.darkColorScheme
import com.example.compose.lightColorScheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    MaterialTheme(
        colorScheme = if(isSystemInDarkTheme()) darkColorScheme else lightColorScheme
    ) {
        HomePresentation()
    }
}