package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditProtocolViewModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditUiEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            AddingNewValueView(
                dataInfo = state.protocol,
                onClickShowPopUpOfReason = { vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfReason(it)) },
                onClickShowPopUpOfTimer = { vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfTimer(it)) },
                onClickShowPopUpOfDate = { vm.onEvent(AddEditEvent.ChangeStatusOfPopUpOfCalendar(it))  },
                onChangeValueIdDocument = { vm.onEvent(AddEditEvent.SetIdOfProtocol(it)) },
                onClickDateOfRealization = { vm.onEvent(AddEditEvent.SetTypeOfPlaningProtocol(it)) },
                onClickUpdateView = { vm.onEvent(AddEditEvent.SetUpProtocol(state.protocol.idDocument)) },
                isEditing = state.isEditingMode
            )
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
}