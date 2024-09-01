package com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import kotlinx.coroutines.flow.Flow

interface ProtocolRepository {
    fun getAllTodayProtocols(startDay: Long, endDay: Long): Flow<List<ProtocolModule>>
    fun getCountOfAllProtocolsInThisMonth(): Flow<Int>
    suspend fun upsertProtocol(protocol: ProtocolModule)
    suspend fun deleteProtocol(protocol: ProtocolModule)
}