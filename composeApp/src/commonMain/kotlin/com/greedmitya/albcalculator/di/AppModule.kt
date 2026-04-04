package com.greedmitya.albcalculator.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.greedmitya.albcalculator.network.AlbionMarketRepository
import com.greedmitya.albcalculator.network.AlbionMarketRepositoryImpl
import com.greedmitya.albcalculator.CraftViewModel
import org.koin.dsl.bind
import org.koin.core.module.dsl.factoryOf
import com.greedmitya.albcalculator.domain.CalculateProfitUseCase

val appModule = module {
    includes(platformModule)
    singleOf(::AlbionMarketRepositoryImpl) bind AlbionMarketRepository::class
    factoryOf(::CalculateProfitUseCase)
    viewModelOf(::CraftViewModel)
}
