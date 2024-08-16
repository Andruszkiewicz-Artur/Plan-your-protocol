package com.andruszkiewiczarturmobileeng.planyourprotocol

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.AddingNewValueView
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.DataInfoItem
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.PopUpOfCalendar
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.PopUpOfReasonDialog
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.PopUpOfTimer
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    var dataInfo by remember { mutableStateOf(ProtocolModule()) }
    val dataInfoList = remember { mutableStateListOf<ProtocolModule>() }
    var popUpOfReasonIsPresented by remember { mutableStateOf(false) }
    var popUpOfTimerPresented by remember { mutableStateOf(false) }
    var popUpOfDatePresented by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current

    MaterialTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Plan Your day"
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val textToCopy = dataInfoList.joinToString(separator = "\n") {
                                    "${it.id} - ${it.date}${if (it.resone != null) " - ${it.resone}" else ""}"
                                }
                                clipboardManager.setText(AnnotatedString(textToCopy))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item {
                    AddingNewValueView(
                        dataInfo = dataInfo,
                        onClickShowPopUpOfReason = { popUpOfReasonIsPresented = it },
                        onClickShowPopUpOfTimer = { popUpOfTimerPresented = it },
                        onClickShowPopUpOfDate = { popUpOfDatePresented = it  },
                        onClickAdd = {
                            val dataFromList = dataInfoList.find { dataInfo.idDocument == it.idDocument }

                            if (dataFromList != null) {
                                dataInfoList[dataInfoList.indexOf(dataFromList)] = dataInfo
                            } else {
                                val lastId = dataInfoList.lastOrNull()?.id ?: -1
                                dataInfo = dataInfo.copy(lastId + 1)
                                dataInfoList.add(dataInfo)
                            }

                            dataInfo = dataInfo.copy(
                                id = null,
                                idDocument = "",
                                time = null,
                                date = null,
                                resone = null
                            )
                        },
                        onChangeValueIdDocument = { dataInfo = dataInfo.copy(idDocument = it) },
                        onClickDateOfRealization = { dataInfo = dataInfo.copy(state = it) },
                        isEditing = dataInfoList.find { it.idDocument == dataInfo.idDocument } != null
                    )
                }

                items(dataInfoList) {
                    DataInfoItem(
                        dataInfo = it,
                        onClickDelete = { dataInfoList.remove(it) },
                        onClickEdit = { dataInfo = it }
                    )

                    if (dataInfoList.last() != it) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }

    AnimatedVisibility(popUpOfReasonIsPresented) {
        PopUpOfReasonDialog(
            onDismiss = { popUpOfReasonIsPresented = false },
            onSave = {
                dataInfo = dataInfo.copy(resone = it)
                popUpOfReasonIsPresented = false
            }
        )
    }

    AnimatedVisibility(popUpOfTimerPresented) {
        PopUpOfTimer(
            onDismiss = { popUpOfTimerPresented = false },
            onSave = {
                dataInfo = dataInfo.copy(time = it)
                popUpOfTimerPresented = false
            }
        )
    }

    AnimatedVisibility(popUpOfDatePresented) {
        PopUpOfCalendar(
            onDismiss = { popUpOfDatePresented = false },
            onSave = {
                dataInfo = dataInfo.copy(date = it)
                popUpOfDatePresented = false
            }
        )
    }
}