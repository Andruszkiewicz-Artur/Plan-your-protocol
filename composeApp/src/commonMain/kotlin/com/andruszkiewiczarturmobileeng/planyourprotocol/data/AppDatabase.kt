package com.andruszkiewiczarturmobileeng.planyourprotocol.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.dao.ProtocolDao
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.HistoricalProtocolEntity
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity.ProtocolEntity

@Database(
    version = 3,
    entities = [ProtocolEntity::class, HistoricalProtocolEntity::class],
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class ProtocolDatabase: RoomDatabase(), DB {

    abstract fun protocolDao(): ProtocolDao
    override fun clearAllTables(): Unit {}

    companion object {
        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL("""
                    CREATE TABLE IF NOT EXISTS HistoricalProtocols (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,    
                        idProtocol TEXT NOT NULL,               
                        state TEXT NOT NULL,                    
                        time INTEGER,                            
                        date INTEGER,                           
                        editingDate INTEGER NOT NULL,            
                        reason TEXT  
                    )
                """)
            }
        }
    }
}

interface DB {
    fun clearAllTables(): Unit {}
}