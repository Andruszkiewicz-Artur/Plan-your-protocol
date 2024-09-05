package com.andruszkiewiczarturmobileeng.planyourprotocol.data

import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolCountByDayModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.RangeOfDataModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.unit.enums.PresentedTypeDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.minus

class ProtocolRepositoryImpl(private val db: ProtocolDatabase): ProtocolRepository {
    override fun getAllTodayProtocols(startDay: Long, endDay: Long): Flow<List<ProtocolModule>> = db.protocolDao().getAllTodayProtocols(startDay, endDay).map { it.map { it.toDomain() } }

    override fun getCountOfAllProtocolsInThisMonth(): Flow<Int> = db.protocolDao().getCountOfAllProtocolsInThisMonth()

    override fun getListOfProtocolCount(presentedTypeDate: PresentedTypeDate, range: RangeOfDataModel): Flow<List<ProtocolCountByDayModel>> {
        val currentTime = Clock.System.now().toEpochMilliseconds()

        return db.protocolDao().getProtocolListCount(
            type = when(presentedTypeDate) {
                PresentedTypeDate.Daily -> "%d.%m.%Y"
                PresentedTypeDate.Monthly -> "%m.%Y"
            },
            min = range.min ?: Instant.fromEpochMilliseconds(currentTime).minus(24*3660, DateTimeUnit.HOUR).toEpochMilliseconds(),
            last = range.last ?: currentTime
        )
    }

    override suspend fun upsertProtocol(protocol: ProtocolModule) = db.protocolDao().upsertProtocol(protocol.toEntity())

    override suspend fun deleteProtocol(protocol: ProtocolModule) = db.protocolDao().deleteProtocol(protocol.toEntity())
}