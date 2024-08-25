package com.andruszkiewiczarturmobileeng.planyourprotocol.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProtocolDao {

    @Query("SELECT * FROM Protocols ORDER BY editingDate DESC")
    fun getAllProtocols(): Flow<List<ProtocolEntity>>

    @Upsert
    suspend fun upsertProtocol(protocol: ProtocolEntity)

    @Delete
    suspend fun deleteProtocol(protocol: ProtocolEntity)

}