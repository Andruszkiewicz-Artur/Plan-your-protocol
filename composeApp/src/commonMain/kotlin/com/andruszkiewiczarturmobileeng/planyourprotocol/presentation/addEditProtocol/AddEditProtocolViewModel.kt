package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andruszkiewiczarturmobileeng.planyourprotocol.controller.SnackbarController
import com.andruszkiewiczarturmobileeng.planyourprotocol.controller.SnackbarEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.CAD
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.Today
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditProtocolViewModel(
    private val repository: ProtocolRepository
): ViewModel() {

    private val _state = MutableStateFlow(AddEditState())
    val state = _state.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<AddEditUiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.SetUpProtocol -> {
                viewModelScope.launch {
                    repository.getProtocolById(event.protocolId)?.let { protocol ->
                        _state.update { it.copy(
                            protocol = protocol,
                            isEditingMode = true
                        ) }
                    }
                }
            }
            is AddEditEvent.SetDate -> {
                if (event.date != null) {
                    _state.update { it.copy(protocol = it.protocol.copy(date = event.date)) }
                }
            }
            is AddEditEvent.SetIdOfProtocol -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            protocol = it.protocol.copy(idDocument = event.idProtocol),
                            isEditingMode = repository.getProtocolById(event.idProtocol) != null
                        )
                    }
                }
            }
            is AddEditEvent.SetReasonOfProtocol -> _state.update { it.copy(protocol = it.protocol.copy(resone = event.reason)) }
            is AddEditEvent.SetTimeOfProtocol -> _state.update { it.copy(protocol = it.protocol.copy(time = event.time)) }
            is AddEditEvent.SetTypeOfPlaningProtocol -> _state.update { it.copy(protocol = it.protocol.copy(state = event.type)) }
            is AddEditEvent.ChangeStatusOfPopUpOfCalendar -> _state.update { it.copy(isPresentedCalendar = event.isPresented) }
            is AddEditEvent.ChangeStatusOfPopUpOfReason -> _state.update { it.copy(isPresentedReasons = event.isPresented) }
            is AddEditEvent.ChangeStatusOfPopUpOfTimer -> _state.update { it.copy(isPresentedTimer = event.isPresented) }
            AddEditEvent.SaveProtocol -> {
                var currentProtocol = _state.value.protocol
                var shouldProceed = false

                viewModelScope.launch {
                    when(currentProtocol.state) {
                        Today -> if (currentProtocol.idDocument.isNotBlank() && currentProtocol.time != null) {
                            shouldProceed = true
                            currentProtocol = currentProtocol.copy(
                                date = null,
                                resone = null
                            )
                        }
                        CAD -> if (currentProtocol.idDocument.isNotBlank() && currentProtocol.date != null && currentProtocol.resone != null) {
                            shouldProceed = true
                            currentProtocol = currentProtocol.copy(
                                time = null
                            )
                        }
                        else -> if (currentProtocol.idDocument.isNotBlank()) {
                            shouldProceed = true
                            currentProtocol = currentProtocol.copy(
                                date = null,
                                resone = null,
                                time = null
                            )
                        }
                    }

                    if (shouldProceed) {
                        repository.upsertProtocol(currentProtocol)

                        _sharedFlow.emit(AddEditUiEvent.BackToHomeScreen)
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = if (state.value.isEditingMode) "Update protocol" else "Add new protocol",
                                action = null
                            )
                        )
                    }
                }
            }
        }
    }
}