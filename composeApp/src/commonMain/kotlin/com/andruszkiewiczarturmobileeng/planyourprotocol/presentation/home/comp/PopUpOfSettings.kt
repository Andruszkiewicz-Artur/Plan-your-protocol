package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.compose.AlertDialogDefault
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.ThemType
import kotlinx.coroutines.launch

@Composable
fun PopUpOfSettings(
    onDismiss: () -> Unit,
    onClickButton: (ThemType) -> Unit
) {
    val scope = rememberCoroutineScope()

    AlertDialogDefault(
        onDismissRequest = onDismiss,
        content = {
            Text(
                text = "Choose your them",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ThemeItem(
                    imageVector = Icons.Outlined.LightMode,
                    value = ThemType.Light.name,
                    onClick = { scope.launch {
                        onClickButton(ThemType.Light)
                    } }
                )

                ThemeItem(
                    imageVector = Icons.Outlined.Contrast,
                    value = ThemType.System.name,
                    onClick = { scope.launch {
                        onClickButton(ThemType.System)
                    } }
                )

                ThemeItem(
                    imageVector = Icons.Outlined.DarkMode,
                    value = ThemType.Dark.name,
                    onClick = { scope.launch {
                        onClickButton(ThemType.Dark)
                    } }
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(
                        text = "Dismiss"
                    )
                }
            }
        }
    )
}