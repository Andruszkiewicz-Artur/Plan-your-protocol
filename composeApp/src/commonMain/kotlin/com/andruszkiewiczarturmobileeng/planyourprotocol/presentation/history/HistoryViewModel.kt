package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.RangeOfDataModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.unit.enums.PresentedTypeDate
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.minus

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
            is HistoryEvent.ChangePresentationOfDataPickerRange -> {
                _state.update { it.copy(
                    isPresentedDataPickerRange = event.isPresented
                ) }
            }
            is HistoryEvent.SetUpRangeOfDate -> {
                if (_state.value.rangeOfDataModel != event.range) {
                    _state.update { it.copy(
                        rangeOfDataModel = event.range.copy(
                            last = event.range.last?.plus(86399999)
                        )
                    ) }

                    getListOfProtocols()
                }

                onEvent(HistoryEvent.ChangePresentationOfDataPickerRange(false))
            }
        }
    }

    private fun getListOfProtocols() {
        _currentProtocolsFlowJob?.cancel()

        _currentProtocolsFlowJob = viewModelScope.launch {
            repository.getListOfProtocolCount(
                type = _state.value.presentedTypeDate,
                range = if(_state.value.presentedTypeDate.equals(PresentedTypeDate.Daily)) _state.value.rangeOfDataModel else RangeOfDataModel(null, null)
            ).collect { list ->
                _state.update {
                    it.copy(
                        protocolsCountByDayList = list
                    )
                }
            }
        }
    }
}