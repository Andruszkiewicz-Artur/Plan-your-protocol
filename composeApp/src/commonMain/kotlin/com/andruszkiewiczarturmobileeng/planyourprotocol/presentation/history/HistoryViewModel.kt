package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType.CAD
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.getStartAndEndOfDay
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.isTodayValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: ProtocolRepository
): ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    private var _currentProtocolsFlowJob: Job? = null

    init {
        getListOfProtocols()
    }

    fun onEvent(event: HistoryEvent) {
        when(event) {
            is HistoryEvent.ChangePresentedType -> {
                if (event.type != _state.value.presentedTypeDate) {
                    _state.update { it.copy(
                        presentedTypeDate = event.type
                    ) }
                    getListOfProtocols()
                }
            }
        }
    }

    private fun getListOfProtocols() {
        _currentProtocolsFlowJob?.cancel()

        _currentProtocolsFlowJob = viewModelScope.launch {
            repository.getListOfProtocolCount(_state.value.presentedTypeDate).collect { list ->
                _state.update {
                    it.copy(
                        protocolsCountByDayList = list
                    )
                }
            }
        }
    }
}