package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

import androidx.compose.ui.platform.ClipboardManager
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule

sealed class HomeEvent {
    //PopUps
    data class ChangeStatusOfPopUpOfReason(val isPresented: Boolean): HomeEvent()
    data class ChangeStatusOfPopUpOfTimer(val isPresented:Boolean): HomeEvent()
    data class ChangeStatusOfPopUpOfCalendar(val isPresented: Boolean, val option: CalendarOption?): HomeEvent()
    data class ChangeStatusOfPopUpOfSettings(val isPresented: Boolean): HomeEvent()

    //Set up data protocol
    data class SetIdOfProtocol(val idProtocol: String):HomeEvent()
    data class SetTypeOfPlaningProtocol(val type: ProtocolRealizationType): HomeEvent()
    data class SetTimeOfProtocol(val time: Int?): HomeEvent()
    data class SetDate(val date: Long?): HomeEvent()
    data class SetReasonOfProtocol(val reason: String): HomeEvent()
    data class ClickCopyData(val clipboardManager: ClipboardManager): HomeEvent()
    data object ClickPresentAddNewValue: HomeEvent()

    //Protocol Manager
    data class ChooseProtocol(val protocol: ProtocolModule): HomeEvent()
    data class DeleteProtocol(val protocol: ProtocolModule): HomeEvent()
    data class SelectProtocol(val protocol: ProtocolModule, val isSelected: Boolean): HomeEvent()
    data class ChangeAllSelection(val select: Boolean): HomeEvent()
    data class ChangeDateListOfProtocols(val isPrevious: Boolean): HomeEvent()
    data class SetProtocol(val protocol: ProtocolModule? = null): HomeEvent()
}