package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.unit.enums.PresentedTypeDate

sealed class HistoryEvent {
    data class ChangePresentedType(val type: PresentedTypeDate): HistoryEvent()
}