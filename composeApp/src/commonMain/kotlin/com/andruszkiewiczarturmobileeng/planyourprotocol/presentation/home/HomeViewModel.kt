package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class HomeViewModel(
    private val repository: ProtocolRepository
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    init {
        getAllProtocols()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ChangeStatusOfPopUpOfCalendar -> _state.update { it.copy(isPresentedCalendar = event.isPresented) }
            is HomeEvent.ChangeStatusOfPopUpOfReason -> _state.update { it.copy(isPresentedReasons = event.isPresented) }
            is HomeEvent.ChangeStatusOfPopUpOfTimer -> _state.update { it.copy(isPresentedTimer = event.isPresented) }
            is HomeEvent.ChooseProtocol -> _state.update { it.copy(currentProtocol = event.protocol) }
            is HomeEvent.DeleteProtocol -> {
                viewModelScope.launch {
                    repository.deleteProtocol(event.protocol)
                }
            }
            is HomeEvent.SetDateOfProtocol -> _state.update { it.copy(currentProtocol = it.currentProtocol.copy(date = event.date)) }
            is HomeEvent.SetIdOfProtocol -> _state.update {
                it.copy(
                    currentProtocol = it.currentProtocol.copy(idDocument = event.idProtocol),
                    isEditing = it.protocolsList.any { it.idDocument == event.idProtocol}
                )
            }
            HomeEvent.SetProtocol -> {
                val currentProtocol = _state.value.currentProtocol
                var shouldProceed = true

                viewModelScope.launch {
                    when(currentProtocol.state) {
                        Today -> if (currentProtocol.idDocument.isBlank() && currentProtocol.time == null) shouldProceed = false
                        CAD -> if (currentProtocol.idDocument.isBlank() && currentProtocol.date == null && currentProtocol.resone == null) shouldProceed = false
                        else -> if (currentProtocol.idDocument.isBlank()) shouldProceed = false
                    }

                    if (shouldProceed) {
                        repository.upsertProtocol(_state.value.currentProtocol.copy(
                            editingDate = Clock.System.now().toEpochMilliseconds()
                        ))
                        _state.update { it.copy(currentProtocol = ProtocolModule()) }
                    }
                }
            }
            is HomeEvent.SetReasonOfProtocol -> _state.update { it.copy(currentProtocol = it.currentProtocol.copy(resone = event.reason)) }
            is HomeEvent.SetTimeOfProtocol -> _state.update { it.copy(currentProtocol = it.currentProtocol.copy(time = event.time)) }
            is HomeEvent.SetTypeOfPlaningProtocol -> _state.update { it.copy(currentProtocol = it.currentProtocol.copy(state = event.type)) }
            is HomeEvent.SelectProtocol -> {
                _state.update { it.copy(
                    protocolsList = it.protocolsList.map {
                        if (it.idDocument == event.protocol.idDocument) it.copy(isSelected = event.isSelected)
                        else it
                    }
                ) }
            }
        }
    }

    private fun getAllProtocols() {
        viewModelScope.launch {
            repository.getAllProtocols().collect { protocols ->
                _state.update { it.copy(protocolsList = protocols) }
            }
        }
    }
}