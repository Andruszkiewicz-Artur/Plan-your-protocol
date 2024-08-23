package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun HomePresentation(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = koinViewModel()
) {
    val state = vm.state.collectAsState().value
    val clipboardManager = LocalClipboardManager.current

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
                            val textToCopy = state.protocolsList.joinToString(separator = "\n") {
                                "${it.idDocument} - ${it.date}${if (it.resone != null) " - ${it.resone}" else ""}"
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
                    dataInfo = state.currentProtocol,
                    onClickShowPopUpOfReason = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfReason(it)) },
                    onClickShowPopUpOfTimer = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfTimer(it)) },
                    onClickShowPopUpOfDate = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(it))  },
                    onClickAdd = { vm.onEvent(HomeEvent.SetProtocol) },
                    onChangeValueIdDocument = { vm.onEvent(HomeEvent.SetIdOfProtocol(it)) },
                    onClickDateOfRealization = { vm.onEvent(HomeEvent.SetTypeOfPlaningProtocol(it)) },
                    isEditing = state.isEditing
                )
            }

            items(state.protocolsList) {
                DataInfoItem(
                    dataInfo = it,
                    onClickDelete = { vm.onEvent(HomeEvent.DeleteProtocol(it)) },
                    onClickEdit = { vm.onEvent(HomeEvent.ChooseProtocol(it)) }
                )

                if (state.protocolsList.last() != it) {
                    HorizontalDivider()
                }
            }
        }
    }

    AnimatedVisibility(state.isPresentedReasons) {
        PopUpOfReasonDialog(
            onDismiss = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfReason(false)) },
            onSave = {
                vm.onEvent(HomeEvent.SetReasonOfProtocol(it))
                vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfReason(false))
            }
        )
    }

    AnimatedVisibility(state.isPresentedTimer) {
        PopUpOfTimer(
            onDismiss = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfTimer(false)) },
            onSave = {
                vm.onEvent(HomeEvent.SetTimeOfProtocol(it))
                vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfTimer(false))
            }
        )
    }

    AnimatedVisibility(state.isPresentedCalendar) {
        PopUpOfCalendar(
            onDismiss = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(false)) },
            onSave = {
                vm.onEvent(HomeEvent.SetDateOfProtocol(it))
                vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(false))
            }
        )
    }
}