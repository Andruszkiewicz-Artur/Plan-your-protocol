package com.andruszkiewiczarturmobileeng.planyourprotocol.data

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getDatabaseBuilder(ctx: Context): ProtocolDatabase {
    val dbFile = ctx.getDatabasePath("protocolDB.db")
    return Room.databaseBuilder<ProtocolDatabase>(ctx, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}