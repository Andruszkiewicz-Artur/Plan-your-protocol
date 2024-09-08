package com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolCountByDayModel

data class ProtocolCountByDayEntity(
    val date: String,
    val count: Int
) {
    fun toDomain(): ProtocolCountByDayModel = ProtocolCountByDayModel(date, count)
}