package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolCountByDayModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.unit.enums.PresentedTypeDate

data class HistoryState(
    val protocolsCountByDayList: List<ProtocolCountByDayModel> = emptyList(),
    val presentedTypeDate: PresentedTypeDate = PresentedTypeDate.Daily
)
