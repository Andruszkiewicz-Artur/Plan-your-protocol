package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule

sealed class HomeEvent {
    //PopUps
    data class ChangeStatusOfPopUpOfReason(val isPresented: Boolean): HomeEvent()
    data class ChangeStatusOfPopUpOfTimer(val isPresented:Boolean): HomeEvent()
    data class ChangeStatusOfPopUpOfCalendar(val isPresented: Boolean): HomeEvent()

    //Set up data protocol
    data class SetIdOfProtocol(val idProtocol: String):HomeEvent()
    data class SetTypeOfPlaningProtocol(val type: ProtocolRealizationType): HomeEvent()
    data class SetTimeOfProtocol(val time: Int?): HomeEvent()
    data class SetDateOfProtocol(val date: Long?): HomeEvent()
    data class SetReasonOfProtocol(val reason: String): HomeEvent()

    //Protocol Manager
    data class ChooseProtocol(val protocol: ProtocolModule): HomeEvent()
    data class DeleteProtocol(val protocol: ProtocolModule): HomeEvent()
    data object SetProtocol: HomeEvent()
}