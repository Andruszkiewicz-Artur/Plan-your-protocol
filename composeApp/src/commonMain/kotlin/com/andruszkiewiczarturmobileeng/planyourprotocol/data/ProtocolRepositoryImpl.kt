package com.andruszkiewiczarturmobileeng.planyourprotocol.data

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProtocolRepositoryImpl(private val db: ProtocolDatabase): ProtocolRepository {
    override fun getAllTodayProtocols(): Flow<List<ProtocolModule>> = db.protocolDao().getAllTodayProtocols().map { it.map { it.toDomain() } }

    override fun getCountOfAllProtocolsInThisMonth(): Flow<Int> = db.protocolDao().getCountOfAllProtocolsInThisMonth()

    override suspend fun upsertProtocol(protocol: ProtocolModule) = db.protocolDao().upsertProtocol(protocol.toEntity())

    override suspend fun deleteProtocol(protocol: ProtocolModule) = db.protocolDao().deleteProtocol(protocol.toEntity())
}