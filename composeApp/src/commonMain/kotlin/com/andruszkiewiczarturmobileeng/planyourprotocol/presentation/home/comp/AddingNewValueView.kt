package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.convertMillisToDate
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.convertToTime

@Composable
fun AddingNewValueView(
    dataInfo: ProtocolModule,
    onClickShowPopUpOfReason: (Boolean) -> Unit = {  },
    onClickShowPopUpOfTimer: (Boolean) -> Unit = {  },
    onClickShowPopUpOfDate: (Boolean) -> Unit = {  },
    onClickAdd: () -> Unit = {  },
    onChangeValueIdDocument: (String) -> Unit = {  },
    onClickDateOfRealization: (ProtocolRealizationType) -> Unit = {  },
    isEditing: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = dataInfo.idDocument,
            onValueChange = { onChangeValueIdDocument(it) },
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
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
        )

        ListOfDataStateView(
            currentState = dataInfo.state,
            onClick = {
                onClickDateOfRealization(it)
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
                                .clickable { onClickShowPopUpOfTimer(true) }
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
                                .clickable { onClickShowPopUpOfDate(true) }
                        )

                        Spacer(Modifier.height(12.dp))

                        OutlinedTextField(
                            value = dataInfo.resone ?: "",
                            onValueChange = {},
                            enabled = false,
                            readOnly = true,
                            placeholder = { Text(text = "Resone...") },
                            modifier = Modifier
                                .clickable { onClickShowPopUpOfReason(true) }
                        )
                    }
                    else -> { /*In others situation view is empty */ }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onClickAdd
        ) {
            AnimatedContent(isEditing) { editing ->
                if (editing) {
                    Text(text = "Update value")
                } else {
                    Text(text = "Add new value")
                }
            }
        }

        Spacer(Modifier.height(12.dp))
    }
}