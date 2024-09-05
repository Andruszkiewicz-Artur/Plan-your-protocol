package com.andruszkiewiczarturmobileeng.planyourprotocol.data

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolCountByDayModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.unit.enums.PresentedTypeDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProtocolRepositoryImpl(private val db: ProtocolDatabase): ProtocolRepository {
    override fun getAllTodayProtocols(startDay: Long, endDay: Long): Flow<List<ProtocolModule>> = db.protocolDao().getAllTodayProtocols(startDay, endDay).map { it.map { it.toDomain() } }

    override fun getCountOfAllProtocolsInThisMonth(): Flow<Int> = db.protocolDao().getCountOfAllProtocolsInThisMonth()

    override fun getListOfProtocolCount(presentedTypeDate: PresentedTypeDate): Flow<List<ProtocolCountByDayModel>> {
        return db.protocolDao().getProtocolCountByDay(
            type = when(presentedTypeDate) {
                PresentedTypeDate.Daily -> "%Y-%m-%d"
                PresentedTypeDate.Monthly -> "%Y-%m"
            }
        )
    }

    override suspend fun upsertProtocol(protocol: ProtocolModule) = db.protocolDao().upsertProtocol(protocol.toEntity())

    override suspend fun deleteProtocol(protocol: ProtocolModule) = db.protocolDao().deleteProtocol(protocol.toEntity())
}