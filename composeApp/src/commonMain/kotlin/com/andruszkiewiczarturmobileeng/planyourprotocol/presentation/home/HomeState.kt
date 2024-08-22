package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule

data class HomeState(
    val currentProtocol: ProtocolModule = ProtocolModule(),
    val protocolsList: List<ProtocolModule> = emptyList(),
    val isPresentedReasons: Boolean = false,
    val isPresentedTimer: Boolean = false,
    val isPresentedCalendar: Boolean = false,
)