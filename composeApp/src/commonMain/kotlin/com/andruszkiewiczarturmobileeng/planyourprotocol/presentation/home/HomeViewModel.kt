package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.CAD
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.Canceled
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.Today
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.convertMillisToDate
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.convertToTime
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.isTodayValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val repository: ProtocolRepository
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    private val _todayDateDay = Clock.System.now().toLocalDateTime(TimeZone.UTC).dayOfMonth

    init {
        getAllProtocols()
        getCountOfProtocolsInThisMonth()
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
                var currentProtocol = _state.value.currentProtocol
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

                _state.update { it.copy(
                    isAllSelected = it.protocolsList.all { it.isSelected }
                ) }
            }
            is HomeEvent.ChangeAllSelection -> {
                _state.update { it.copy(
                    protocolsList = it.protocolsList.map { it.copy(isSelected = event.select) },
                    isAllSelected = event.select
                ) }
            }
            is HomeEvent.ClickCopyData -> {
                val textToCopy = _state.value.protocolsList
                    .mapNotNull { if (it.isSelected) it else null}
                    .joinToString("\n") {
                        "${it.idDocument} - " + when(it.state) {
                            Today -> it.time?.convertToTime()
                            CAD -> "CAD ${it.date?.convertMillisToDate()} - ${it.resone}"
                            Canceled -> "Anulowane"
                            else -> it.state.name
                        }
                    }

                event.clipboardManager.setText(AnnotatedString(textToCopy))
            }
            HomeEvent.ClickPresentAddNewValue -> {
                _state.update { it.copy(
                    isPresentedAddNewProtocol = !it.isPresentedAddNewProtocol
                ) }
            }
        }
    }

    private fun getAllProtocols() {
        viewModelScope.launch {
            repository.getAllTodayProtocols().collect { protocols ->
                _state.update { it.copy(protocolsList = protocols.map {
                    it.copy(
                        cadForToday = it.state == CAD && it.date?.isTodayValue(_todayDateDay) ?: false
                    )
                }) }
            }
        }
    }

    private fun getCountOfProtocolsInThisMonth() {
        viewModelScope.launch {
            repository.getCountOfAllProtocolsInThisMonth().collect { count ->
                _state.update { it.copy(protocolsInThisMonth = count) }
            }
        }
    }
}