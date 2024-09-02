package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.compose.AlertDialogDefault
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ThemType
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
                    imageVector = Icons.Filled.LightMode,
                    value = ThemType.Light.name,
                    onClick = { scope.launch {
                        onClickButton(ThemType.Light)
                    } }
                )

                ThemeItem(
                    imageVector = Icons.Filled.Contrast,
                    value = ThemType.System.name,
                    onClick = { scope.launch {
                        onClickButton(ThemType.System)
                    } }
                )

                ThemeItem(
                    imageVector = Icons.Filled.DarkMode,
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

@Composable
fun ThemeItem(
    imageVector: ImageVector,
    value: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = {
            onClick()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(70.dp)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = value,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )
        }
    }
}