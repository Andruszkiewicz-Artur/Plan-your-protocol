package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditProtocolViewModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditUiEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertMillisToDate
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertToTime
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import qrscanner.CameraLens
import qrscanner.OverlayShape
import qrscanner.QrScanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProtocolPresentation(
    navHostController: NavHostController,
    idProtocol: String?,
    vm: AddEditProtocolViewModel = koinViewModel()
) {
    val state = vm.state.collectAsState().value

    LaunchedEffect(Unit) {
        if (idProtocol != null) vm.onEvent(AddEditEvent.SetUpProtocol(idProtocol))

        vm.sharedFlow.collectLatest { event ->
            when (event) {
                AddEditUiEvent.BackToHomeScreen -> navHostController.popBackStack()
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.onEvent(AddEditEvent.SaveProtocol)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = null
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AnimatedContent(state.isEditingMode) { isEditing ->
                        if (isEditing) {
                            Text(
                                text = "Editing protocol"
                            )
                        } else {
                            Text(
                                text = "Adding new protocol"
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navHostController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            AddingNewValueView(
                dataInfo = state.protocol,
                presentUploadButton = state.updateProtocol.idDocument == state.protocol.idDocument,
                isEditing = state.isEditingMode,
                onClickEvent = { vm.onEvent(it) }
            )

            AnimatedVisibility(state.updateProtocol.idDocument == state.protocol.idDocument) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "Protocol history",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            if (state.listOfHistoricalProtocols.isEmpty()) {
                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Empty history list!",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                                    )
                                }
                            }
                        }

                        items(state.listOfHistoricalProtocols) { protocol ->
                            HistoricalItem(
                                protocol = protocol,
                                isLast = state.listOfHistoricalProtocols.last() == protocol
                            )
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(state.isPresentedReasons) {
        PopUpOfReasonDialog(
            onDismiss = { vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfReason(false)) },
            onSave = {
                vm.onEvent(AddEditEvent.SetReasonOfProtocol(it))
                vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfReason(false))
            }
        )
    }

    AnimatedVisibility(state.isPresentedTimer) {
        PopUpOfTimer(
            onDismiss = { vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfTimer(false)) },
            onSave = {
                vm.onEvent(AddEditEvent.SetTimeOfProtocol(it))
                vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfTimer(false))
            }
        )
    }

    AnimatedVisibility(state.isPresentedCalendar) {
        PopUpOfCalendar(
            onDismiss = { vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfCalendar(false)) },
            onSave = {
                vm.onEvent(AddEditEvent.SetDate(it))
                vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfCalendar(false))
            }
        )
    }

    AnimatedVisibility(state.isPresentedQrScanner) {
        QrScannerView(
            onEvent = { vm.onEvent(it) }
        )
    }
}