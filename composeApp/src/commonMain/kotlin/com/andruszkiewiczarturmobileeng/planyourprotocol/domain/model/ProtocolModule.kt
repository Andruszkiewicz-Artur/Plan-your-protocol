package com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model

import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolEntity
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.DataInfoRealizationDate
import kotlinx.datetime.Clock

data class ProtocolModule(
    var id: Long? = null,
    var idDocument: String = "",
    var state: DataInfoRealizationDate = DataInfoRealizationDate.Today,
    var time: Int? = null,
    var date: Long? = null,
    var resone: String? = null
) {
    fun toEntity() = ProtocolEntity(
            id = id,
            idDocument = idDocument,
            state = state,
            time = time,
            date = date ?: Clock.System.now().toEpochMilliseconds(),
            resone = resone
        )
}
