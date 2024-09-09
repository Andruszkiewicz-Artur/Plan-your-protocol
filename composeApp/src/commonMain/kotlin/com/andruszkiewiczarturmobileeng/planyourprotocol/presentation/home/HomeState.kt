package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import kotlinx.datetime.Clock

data class HomeState(
    val currentProtocol: ProtocolModule = ProtocolModule(),
    val protocolsList: List<ProtocolModule> = emptyList(),
    val isPresentedCalendar: Boolean = false,
    val isPresentedSettings: Boolean = false,
    val isEditing: Boolean = false,
    val protocolsInThisMonth: Int = 0,
    val isAllSelected: Boolean = false,
    val isPresentedAddNewProtocol: Boolean = true,
    val currentDatePresenting: Long = Clock.System.now().toEpochMilliseconds(),
    val isSearchValue: Boolean = false,
    val searchValue: String = "",
    val searchingList: List<ProtocolModule> = emptyList()
)
