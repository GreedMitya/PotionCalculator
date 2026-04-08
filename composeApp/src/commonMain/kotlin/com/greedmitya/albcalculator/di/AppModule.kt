package com.greedmitya.albcalculator.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.greedmitya.albcalculator.network.AlbionMarketRepository
import com.greedmitya.albcalculator.network.AlbionMarketRepositoryImpl
import com.greedmitya.albcalculator.CraftViewModel
import com.greedmitya.albcalculator.MarketsViewModel
import org.koin.dsl.bind
import org.koin.core.module.dsl.factoryOf
import com.greedmitya.albcalculator.domain.CalculateProfitUseCase
import com.greedmitya.albcalculator.domain.FetchMarketDataUseCase
import com.greedmitya.albcalculator.domain.FetchPricesUseCase
import com.greedmitya.albcalculator.domain.PotionAdvisorUseCase
import com.greedmitya.albcalculator.i18n.GameNameProvider
import com.greedmitya.albcalculator.i18n.JsonGameNameProvider

val appModule = module {
    includes(platformModule)
    singleOf(::AlbionMarketRepositoryImpl) bind AlbionMarketRepository::class
    factoryOf(::CalculateProfitUseCase)
    factoryOf(::FetchPricesUseCase)
    factoryOf(::FetchMarketDataUseCase)
    factoryOf(::PotionAdvisorUseCase)
    viewModelOf(::CraftViewModel)
    viewModelOf(::MarketsViewModel)
    singleOf(::JsonGameNameProvider) bind GameNameProvider::class
}
