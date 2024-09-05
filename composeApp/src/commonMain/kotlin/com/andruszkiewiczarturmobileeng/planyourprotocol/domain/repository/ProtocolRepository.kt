package com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolCountByDayModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.unit.enums.PresentedTypeDate
import kotlinx.coroutines.flow.Flow

interface ProtocolRepository {
    fun getAllTodayProtocols(startDay: Long, endDay: Long): Flow<List<ProtocolModule>>
    fun getCountOfAllProtocolsInThisMonth(): Flow<Int>
    fun getListOfProtocolCount(type: PresentedTypeDate): Flow<List<ProtocolCountByDayModel>>
    suspend fun upsertProtocol(protocol: ProtocolModule)
    suspend fun deleteProtocol(protocol: ProtocolModule)
}