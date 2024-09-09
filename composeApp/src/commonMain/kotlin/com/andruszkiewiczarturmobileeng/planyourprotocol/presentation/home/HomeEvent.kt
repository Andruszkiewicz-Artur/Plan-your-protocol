package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

import androidx.compose.ui.platform.ClipboardManager
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditEvent

sealed class HomeEvent {
    //PopUps
    data class ChangeStatusOfPopUpOfCalendar(val isPresented: Boolean): HomeEvent()
    data class ChangeStatusOfPopUpOfSettings(val isPresented: Boolean): HomeEvent()

    //Protocol Manager
    data class DeleteProtocol(val protocol: ProtocolModule): HomeEvent()
    data class SelectProtocol(val protocol: ProtocolModule, val isSelected: Boolean): HomeEvent()
    data class ChangeAllSelection(val select: Boolean): HomeEvent()
    data class SetDate(val date: Long?): HomeEvent()
    data class ChangeDateListOfProtocols(val isPrevious: Boolean): HomeEvent()
    data class ClickCopyData(val clipboardManager: ClipboardManager): HomeEvent()

    //Search
    data class ChangeSearchValue(val value: String): HomeEvent()
    data class ChangeSearchState(val isSearching: Boolean): HomeEvent()
}