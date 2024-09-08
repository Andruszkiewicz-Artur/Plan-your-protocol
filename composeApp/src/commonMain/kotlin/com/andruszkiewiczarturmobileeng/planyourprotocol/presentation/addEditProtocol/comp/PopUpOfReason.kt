package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.compose.AlertDialogDefault

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PopUpOfReasonDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var chosenReason by remember { mutableStateOf("") }
    val listOfReasons = listOf(
        "Użytkownik na urlopie",
        "użytkownik na L4",
        "użytkownik w delegacji",
        "użytkownik prosi o serwis jak będziemy w okolicy",
        "użytkownik prosi o serwis jak przyjdą części do innych zleceń w tym samym zakładzie"
    )
    var ownReason by remember { mutableStateOf("") }

    AlertDialogDefault(
        onDismissRequest = onDismiss,
        content = {
            Text(
                text = "Choose your resone",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            FlowRow (
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                listOfReasons.forEach { reason ->
                    AnimatedContent(
                        targetState = chosenReason,
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        if (chosenReason != reason) {
                            OutlinedButton(
                                onClick = {
                                    chosenReason = reason
                                }
                            ) {
                                Text(
                                    text = reason
                                )
                            }
                        } else {
                            Button(
                                onClick = {
                                    chosenReason = reason
                                }
                            ) {
                                Text(
                                    text = reason
                                )
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                label = {
                    Text(text = "Inny powód...")
                },
                value = ownReason,
                onValueChange = { ownReason = it },
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onSave(ownReason)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Save,
                            contentDescription = null
                        )
                    }
                }
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(text = "Cancel")
                }

                Spacer(Modifier.width(8.dp))

                TextButton(
                    onClick = { onSave(chosenReason) }
                ) {
                    Text(text = "Save")
                }
            }
        }
    )
}