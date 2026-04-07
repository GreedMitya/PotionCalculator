package com.greedmitya.albcalculator.di

import android.content.Context
import com.greedmitya.albcalculator.domain.AppPremiumRepository
import com.greedmitya.albcalculator.domain.FavoritesRepository
import com.greedmitya.albcalculator.storage.DataStoreFavoritesRepository
import com.greedmitya.albcalculator.storage.GooglePlayPremiumRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        HttpClient(CIO)
    }
    single {
        DataStoreFavoritesRepository(context = get<Context>())
    } bind FavoritesRepository::class
    single {
        GooglePlayPremiumRepository(context = get<Context>())
    } bind AppPremiumRepository::class
}
