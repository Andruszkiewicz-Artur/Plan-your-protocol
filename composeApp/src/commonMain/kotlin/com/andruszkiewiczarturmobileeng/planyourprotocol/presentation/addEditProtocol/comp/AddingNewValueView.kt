package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.ListOfDataStateView
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertMillisToDate
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertToTime
import qrscanner.CameraLens
import qrscanner.OverlayShape
import qrscanner.QrScanner

@Composable
fun AddingNewValueView(
    dataInfo: ProtocolModule,
    onClickEvent: (AddEditEvent) -> Unit,
    presentUploadButton: Boolean,
    isEditing: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = dataInfo.idDocument,
                onValueChange = { onClickEvent(AddEditEvent.SetIdOfProtocol(it)) },
                placeholder = {
                    Text(
                        text = "Id..."
                    )
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.None,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    Row {
                        IconButton(
                            onClick = {
                                onClickEvent(AddEditEvent.ChangeStatusOfPopUpOfQrCodeScanner(true))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.QrCodeScanner,
                                contentDescription = null
                            )
                        }

                        AnimatedVisibility(isEditing && !presentUploadButton) {
                            IconButton(
                                onClick = { onClickEvent(AddEditEvent.SetUpProtocol(dataInfo.idDocument)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Upload,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                },
            )

            ListOfDataStateView(
                currentState = dataInfo.state,
                onClick = {
                    onClickEvent(AddEditEvent.SetTypeOfPlaningProtocol(it))
                }
            )

            AnimatedContent(dataInfo.state) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (dataInfo.state) {
                        ProtocolRealizationType.Today -> {
                            Spacer(Modifier.height(12.dp))

                            OutlinedTextField(
                                value = dataInfo.time?.convertToTime() ?: "",
                                onValueChange = {},
                                enabled = false,
                                readOnly = true,
                                placeholder = { Text(text = "Time...") },
                                modifier = Modifier
                                    .clickable { onClickEvent(AddEditEvent.ChangeStatusOfPopUpOfTimer(true)) }
                            )
                        }

                        ProtocolRealizationType.CAD -> {
                            Spacer(Modifier.height(12.dp))

                            OutlinedTextField(
                                value = dataInfo.date?.convertMillisToDate() ?: "",
                                onValueChange = {},
                                enabled = false,
                                readOnly = true,
                                placeholder = { Text(text = "Date...") },
                                modifier = Modifier
                                    .clickable { onClickEvent(AddEditEvent.ChangeStatusOfPopUpOfCalendar(true)) }
                            )

                            Spacer(Modifier.height(12.dp))

                            OutlinedTextField(
                                value = dataInfo.resone ?: "",
                                onValueChange = {},
                                enabled = false,
                                readOnly = true,
                                placeholder = { Text(text = "Resone...") },
                                modifier = Modifier
                                    .clickable { onClickEvent(AddEditEvent.ChangeStatusOfPopUpOfReason(true)) }
                            )
                        }

                        else -> { /*In others situation view is empty */
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = dataInfo.description,
                        onValueChange = { onClickEvent(AddEditEvent.SetUpDescription(it)) },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.None,
                            imeAction = ImeAction.Done
                        ),
                        placeholder = {
                            Text(
                                text = "Description..."
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}