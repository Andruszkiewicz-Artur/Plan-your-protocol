package com.andruszkiewiczarturmobileeng.planyourprotocol.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.dao.ProtocolDao
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolEntity

@Database(
    entities = [ProtocolEntity::class],
    version = 1
)
abstract class ProtocolDatabase: RoomDatabase(), DB {

    abstract fun protocolDao(): ProtocolDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}