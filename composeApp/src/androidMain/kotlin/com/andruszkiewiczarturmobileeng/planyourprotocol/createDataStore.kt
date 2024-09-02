package com.andruszkiewiczarturmobileeng.planyourprotocol

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.DATA_STORE_FILE_NAME

fun createDataStore(context: Context): DataStore<Preferences> =
    com.andruszkiewiczarturmobileeng.planyourprotocol.core.createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }