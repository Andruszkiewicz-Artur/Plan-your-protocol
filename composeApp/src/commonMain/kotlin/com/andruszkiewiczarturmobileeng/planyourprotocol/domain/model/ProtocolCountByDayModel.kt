package com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model

import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolCountByDayEntity

data class ProtocolCountByDayModel(
    val date: String,
    val count: Int
)  {
    fun toEntity(): ProtocolCountByDayEntity = ProtocolCountByDayEntity(date, count)
}
