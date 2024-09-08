package com.andruszkiewiczarturmobileeng.planyourprotocol.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.HistoricalProtocolEntity
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolCountByDayEntity
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolEntity
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolCountByDayModel
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

    @Query("""
        SELECT strftime(:type, editingDate / 1000, 'unixepoch') AS date, COUNT(*) AS count
        FROM Protocols
        WHERE time IS NOT NULL
        AND editingDate BETWEEN :min AND :last
        GROUP BY strftime(:type, editingDate / 1000, 'unixepoch')
        ORDER BY strftime(:type, editingDate / 1000, 'unixepoch') DESC
    """)
    fun getProtocolListCount(type: String, min: Long, last: Long): Flow<List<ProtocolCountByDayEntity>>

    @Query("SELECT * FROM Protocols WHERE idDocument = :idProtocol")
    suspend fun getProtocolById(idProtocol: String): ProtocolEntity?

    @Upsert
    suspend fun upsertProtocol(protocol: ProtocolEntity)

    @Delete
    suspend fun deleteProtocol(protocol: ProtocolEntity)

    @Upsert
    suspend fun upsertHistoryProtocol(historicalProtocolEntity: HistoricalProtocolEntity)

    @Query("SELECT * FROM HistoricalProtocols WHERE idProtocol = :idProtocol ORDER BY editingDate DESC")
    suspend fun getAllHistoryProtocolsById(idProtocol: String): List<HistoricalProtocolEntity>
}