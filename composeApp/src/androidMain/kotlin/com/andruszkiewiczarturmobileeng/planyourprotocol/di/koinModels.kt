package com.andruszkiewiczarturmobileeng.planyourprotocol.di

import com.andruszkiewiczarturmobileeng.planyourprotocol.data.ProtocolDatabase
import com.andruszkiewiczarturmobileeng.planyourprotocol.data.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<ProtocolDatabase> { getDatabaseBuilder(get()) }
}