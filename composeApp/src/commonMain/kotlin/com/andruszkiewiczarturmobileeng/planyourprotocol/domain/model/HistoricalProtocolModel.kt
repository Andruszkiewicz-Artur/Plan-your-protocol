package com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model

import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.HistoricalProtocolEntity
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType
import kotlinx.datetime.Clock

data class HistoricalProtocolModel(
    val idProtocol: String,
    val state: ProtocolRealizationType,
    val time: Int?,
    val date: Long?,
    val editingDate: Long?,
    val reason: String?,
) {
    fun toEntity(): HistoricalProtocolEntity = HistoricalProtocolEntity(
        id = null,
        idProtocol = idProtocol,
        state = state,
        time = time,
        date = date,
        reason = reason,
        editingDate = Clock.System.now().toEpochMilliseconds()
    )
}