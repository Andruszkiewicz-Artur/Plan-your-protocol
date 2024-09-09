package com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model

import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolEntity
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType
import kotlinx.datetime.Clock

data class ProtocolModule(
    val idDocument: String = "",
    val state: ProtocolRealizationType = ProtocolRealizationType.Today,
    val time: Int? = null,
    val date: Long? = null,
    val resone: String? = null,
    val editingDate: Long? = null,
    val isSelected: Boolean = false,
    val description: String = "",
    val cadForToday: Boolean = false,
) {
    fun toEntity() = ProtocolEntity(
            idDocument = idDocument,
            state = state,
            time = time,
            date = date,
            resone = resone,
            description = description,
            editingDate = Clock.System.now().toEpochMilliseconds()
        )

    fun toHistoricalProtocolModel(): HistoricalProtocolModel = HistoricalProtocolModel(
        idProtocol = idDocument,
        state = state,
        time = time,
        date = date,
        editingDate = editingDate,
        reason = resone
    )
}
