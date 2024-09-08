package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import kotlinx.datetime.Clock

data class AddEditState(
    val protocol: ProtocolModule = ProtocolModule(),
    var currentDatePresenting: Long = Clock.System.now().toEpochMilliseconds(),
    val isPresentedReasons: Boolean = false,
    val isPresentedTimer: Boolean = false,
    val isPresentedCalendar: Boolean = false,
    val isEditingMode: Boolean = false
)
