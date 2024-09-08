package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol

import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType

sealed class AddEditEvent {
    //Set up protocol
    data class SetUpProtocol(val protocolId: String): AddEditEvent()

    //Set up data protocol
    data class SetIdOfProtocol(val idProtocol: String): AddEditEvent()
    data class SetTypeOfPlaningProtocol(val type: ProtocolRealizationType): AddEditEvent()
    data class SetTimeOfProtocol(val time: Int?): AddEditEvent()
    data class SetDate(val date: Long?): AddEditEvent()
    data class SetReasonOfProtocol(val reason: String): AddEditEvent()
    data object SaveProtocol: AddEditEvent()

    //PopUps
    data class ChangeStatusOfPopUpOfReason(val isPresented: Boolean): AddEditEvent()
    data class ChangeStatusOfPopUpOfTimer(val isPresented:Boolean): AddEditEvent()
    data class ChangeStatusOfPopUpOfCalendar(val isPresented: Boolean): AddEditEvent()
}