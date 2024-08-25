package com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import kotlinx.coroutines.flow.Flow

interface ProtocolRepository {
    fun getAllTodaysProtocols(): Flow<List<ProtocolModule>>
    suspend fun upsertProtocol(protocol: ProtocolModule)
    suspend fun deleteProtocol(protocol: ProtocolModule)
}