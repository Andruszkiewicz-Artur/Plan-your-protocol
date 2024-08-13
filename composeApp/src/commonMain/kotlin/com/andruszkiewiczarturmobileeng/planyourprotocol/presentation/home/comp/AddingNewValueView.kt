package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.DataInfoModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.DataInfoRealizationDate

@Composable
fun AddingNewValueView(
    dataInfo: DataInfoModel,
    onClickShowPopUpOfReason: (Boolean) -> Unit = {  },
    onClickShowPopUpOfTimer: (Boolean) -> Unit = {  },
    onClickShowPopUpOfDate: (Boolean) -> Unit = {  },
    onClickAdd: () -> Unit = {  },
    onChangeValueIdDocument: (String) -> Unit = {  },
    onClickDateOfRealization: (DataInfoRealizationDate) -> Unit = {  },
    isEditing: Boolean = true
) {
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
            }
        )

        ListOfDataStateView(
            currentState = dataInfo.state,
            onClick = {
                onClickDateOfRealization(it)
            }
        )

        Spacer(Modifier.height(12.dp))

        AnimatedContent(dataInfo.state) {
            Column {
                when (dataInfo.state) {
                    DataInfoRealizationDate.Today -> {
                        OutlinedTextField(
                            value = dataInfo.time,
                            onValueChange = {},
                            enabled = false,
                            readOnly = true,
                            placeholder = { Text(text = "Time...") },
                            modifier = Modifier
                                .clickable { onClickShowPopUpOfTimer(true) }
                        )
                    }
                    DataInfoRealizationDate.CAD -> {
                        OutlinedTextField(
                            value = dataInfo.date,
                            onValueChange = {},
                            enabled = false,
                            readOnly = true,
                            placeholder = { Text(text = "Date...") },
                            modifier = Modifier
                                .clickable { onClickShowPopUpOfDate(true) }
                        )

                        Spacer(Modifier.height(12.dp))

                        OutlinedTextField(
                            value = dataInfo.resone,
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

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onClickAdd
        ) {
            Text(text = if (isEditing) "Edit value" else "Add new value")
        }
    }
}