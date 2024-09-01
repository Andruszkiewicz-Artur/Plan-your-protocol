package com.andruszkiewiczarturmobileeng.planyourprotocol.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProtocolDao {
    @Query("""
        SELECT * FROM Protocols
        WHERE ((editingDate BETWEEN :startDay AND :endDay) OR (date BETWEEN :startDay AND :endDay))
        OR (state = 'PNA' OR state = 'CNA')
        ORDER BY editingDate DESC, date DESC
    """)
    fun getAllTodayProtocols(startDay: Long, endDay: Long): Flow<List<ProtocolEntity>>

    @Query("""
        SELECT COUNT(*) FROM Protocols
        WHERE
            time IS NOT NULL AND
            editingDate BETWEEN (strftime('%s', 'now', 'start of month') * 1000)
            AND (strftime('%s', 'now', 'start of month', '+1 month', '-1 second') * 1000)
    """)
    fun getCountOfAllProtocolsInThisMonth(): Flow<Int>

    @Upsert
    suspend fun upsertProtocol(protocol: ProtocolEntity)

    @Delete
    suspend fun deleteProtocol(protocol: ProtocolEntity)
}