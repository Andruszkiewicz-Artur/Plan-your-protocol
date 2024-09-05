package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.RangeOfDataModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.unit.enums.PresentedTypeDate

sealed class HistoryEvent {
    data class ChangePresentedType(val type: PresentedTypeDate): HistoryEvent()
    data class ChangePresentationOfDataPickerRange(val isPresented: Boolean): HistoryEvent()
    data class SetUpRangeOfDate(val range: RangeOfDataModel): HistoryEvent()
}