package com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model

import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolEntity
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType
import kotlinx.datetime.Clock

data class ProtocolModule(
    var idDocument: String = "",
    var state: ProtocolRealizationType = ProtocolRealizationType.Today,
    var time: Int? = null,
    var date: Long? = null,
    var resone: String? = null
) {
    fun toEntity() = ProtocolEntity(
            idDocument = idDocument,
            state = state,
            time = time,
            date = date ?: Clock.System.now().toEpochMilliseconds(),
            resone = resone
        )
}
