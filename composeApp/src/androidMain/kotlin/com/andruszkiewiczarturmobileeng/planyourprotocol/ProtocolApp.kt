package com.andruszkiewiczarturmobileeng.planyourprotocol

import android.app.Application
import com.andruszkiewiczarturmobileeng.planyourprotocol.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class ProtocolApp: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@ProtocolApp)
        }
    }
}