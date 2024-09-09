package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andruszkiewiczarturmobileeng.planyourprotocol.controller.SnackbarAction
import com.andruszkiewiczarturmobileeng.planyourprotocol.controller.SnackbarController
import com.andruszkiewiczarturmobileeng.planyourprotocol.controller.SnackbarEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.CAD
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.Canceled
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.Today
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertMillisToDate
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertToTime
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.getStartAndEndOfDay
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.isTodayValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val repository: ProtocolRepository
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    private val _todayDateDay = Clock.System.now().toLocalDateTime(TimeZone.UTC).dayOfMonth
    private var _currentProtocolsFlowJob: Job? = null

    init {
        getAllProtocols()
        getCountOfProtocolsInThisMonth()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ChangeStatusOfPopUpOfCalendar -> {
                _state.update { it.copy(
                    isPresentedCalendar = event.isPresented
                ) }
            }
            is HomeEvent.DeleteProtocol -> {
                viewModelScope.launch {
                    repository.deleteProtocol(event.protocol)

                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = "Delete Protocol!",
                            action = SnackbarAction(
                                name = "Undo",
                                action = {
                                    viewModelScope.launch {
                                        repository.upsertProtocol(event.protocol)
                                    }
                                }
                            )
                        )
                    )
                }
            }
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

                viewModelScope.launch {
                    if (textToCopy.isNotBlank()) {
                        event.clipboardManager.setText(AnnotatedString(textToCopy))

                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = "Copy protocols!",
                                action = null
                            )
                        )
                    } else {
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = "You need select what do you want to copy!",
                                action = null
                            )
                        )
                    }
                }
            }
            is HomeEvent.ChangeDateListOfProtocols -> {
                _state.update { it.copy(
                    currentDatePresenting = if (event.isPrevious) {
                        Instant.fromEpochMilliseconds(it.currentDatePresenting).minus(24, DateTimeUnit.HOUR).toEpochMilliseconds()
                    } else {
                        Instant.fromEpochMilliseconds(it.currentDatePresenting).plus(24, DateTimeUnit.HOUR).toEpochMilliseconds()
                    }
                ) }

                getAllProtocols()
            }
            is HomeEvent.ChangeStatusOfPopUpOfSettings -> {
                _state.update { it.copy(
                    isPresentedSettings = event.isPresented
                ) }
            }
            is HomeEvent.ChangeSearchState -> {
                if (event.isSearching != _state.value.isSearchValue) {
                    _state.update { it.copy(
                        isSearchValue = event.isSearching,
                        searchValue = "",
                        searchingList = emptyList()
                    ) }
                }
            }
            is HomeEvent.ChangeSearchValue -> {
                _state.update { it.copy(
                    searchValue = event.value
                ) }

                if (_state.value.searchValue.isNotBlank()) getListOfSearchProtocols()
                else _state.update { it.copy(searchingList = emptyList()) }
            }
            is HomeEvent.SetDate -> {
                _state.update { it.copy(
                    currentDatePresenting = event.date ?: it.currentDatePresenting
                ) }

                getAllProtocols()
            }
        }
    }

    private fun getAllProtocols() {
        _currentProtocolsFlowJob?.cancel()
        val startAndEndOfTheDay = _state.value.currentDatePresenting.getStartAndEndOfDay()

        _currentProtocolsFlowJob = viewModelScope.launch {
            repository.getAllTodayProtocols(startDay = startAndEndOfTheDay.first, endDay = startAndEndOfTheDay.second).collect { protocols ->
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

    private fun getListOfSearchProtocols() {
        viewModelScope.launch {
            _state.update { it.copy(
                searchingList = repository.searchListOfProtocol(_state.value.searchValue)
            ) }
        }
    }
}