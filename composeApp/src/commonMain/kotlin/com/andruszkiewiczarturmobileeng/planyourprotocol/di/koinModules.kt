package com.andruszkiewiczarturmobileeng.planyourprotocol.di

import com.andruszkiewiczarturmobileeng.planyourprotocol.data.ProtocolRepositoryImpl
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.repository.ProtocolRepository
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module

val listOfModules: List<Module>
    get() = listOf(
        repositories,
        viewModels,
        platformModule()
    )

private val repositories = module {
    singleOf(::ProtocolRepositoryImpl).bind(ProtocolRepository::class)
}

private val viewModels = module {
    viewModelOf(::HomeViewModel)
}
