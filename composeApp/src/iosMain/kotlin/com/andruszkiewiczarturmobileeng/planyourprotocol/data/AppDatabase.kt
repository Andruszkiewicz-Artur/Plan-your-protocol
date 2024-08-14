package com.andruszkiewiczarturmobileeng.planyourprotocol.data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): ProtocolDatabase {
    val dbFile = "${NSHomeDirectory()}/protocolDB.db"
    return Room.databaseBuilder<ProtocolDatabase>(
        name = dbFile,
        factory = { ProtocolDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}