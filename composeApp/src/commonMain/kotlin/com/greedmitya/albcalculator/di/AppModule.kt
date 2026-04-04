package com.greedmitya.albcalculator.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.greedmitya.albcalculator.network.AlbionMarketRepository
import com.greedmitya.albcalculator.network.AlbionMarketRepositoryImpl
import com.greedmitya.albcalculator.CraftViewModel
import org.koin.dsl.bind
import org.koin.core.module.dsl.factoryOf
import com.greedmitya.albcalculator.domain.CalculateProfitUseCase
import com.greedmitya.albcalculator.domain.FetchPricesUseCase

val appModule = module {
    includes(platformModule)
    singleOf(::AlbionMarketRepositoryImpl) bind AlbionMarketRepository::class
    factoryOf(::CalculateProfitUseCase)
    factoryOf(::FetchPricesUseCase)
    viewModelOf(::CraftViewModel)
}
