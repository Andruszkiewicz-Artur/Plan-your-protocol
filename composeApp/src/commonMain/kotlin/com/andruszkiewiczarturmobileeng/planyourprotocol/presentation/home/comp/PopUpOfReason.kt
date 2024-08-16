package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PopUpOfReasonDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    properties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
) {
    var chosenReason by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    val listOfReasons = listOf(
        "Użytkownik na urlopie",
        "użytkownik na L4",
        "użytkownik w delegacji",
        "użytkownik jest dostępny tylko do ",
        "użytkownik prosi o serwis jak będziemy w okolicy",
        "użytkownik prosi o serwis jak przyjdą części do innych zleceń w tym samym zakładzie"
    )

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = properties
    ) {
        OutlinedCard {
            Column{
                Text(
                    text = "Choose your resone",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                FlowRow (
                    modifier = Modifier
                        .padding(12.dp)
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

                AnimatedVisibility(chosenReason == "użytkownik jest dostępny tylko do") {
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it }
                    )
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onDismiss
                    ) {
                        Text(text = "Cancel")
                    }

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (chosenReason == "użytkownik jest dostępny tylko do") {
                                onSave("$chosenReason $time")
                            } else {
                                onSave(chosenReason)
                            }
                        }
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}