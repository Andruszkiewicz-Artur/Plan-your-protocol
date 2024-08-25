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
        WHERE czas BETWEEN (strftime('%s', 'now', 'start of day') * 1000)
        AND (strftime('%s', 'now', 'start of day', '+1 day') * 1000 - 1)
        ORDER BY editingDate DESC
    """)
    fun getAllTodaysProtocols(): Flow<List<ProtocolEntity>>

    @Upsert
    suspend fun upsertProtocol(protocol: ProtocolEntity)

    @Delete
    suspend fun deleteProtocol(protocol: ProtocolEntity)

}