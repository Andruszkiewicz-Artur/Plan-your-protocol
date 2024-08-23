package com.andruszkiewiczarturmobileeng.planyourprotocol.data

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProtocolRepositoryImpl(private val db: ProtocolDatabase): ProtocolRepository {
    override fun getAllProtocols(): Flow<List<ProtocolModule>> = db.protocolDao().getAllProtocols().map { it.map { it.toDomain() } }

    override suspend fun upsertProtocol(protocol: ProtocolModule) = db.protocolDao().upsertProtocol(protocol.toEntity())

    override suspend fun deleteProtocol(protocol: ProtocolModule) = db.protocolDao().deleteProtocol(protocol.toEntity())
}