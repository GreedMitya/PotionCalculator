package com.greedmitya.albcalculator.di

import com.greedmitya.albcalculator.domain.FavoritesRepository
import com.greedmitya.albcalculator.domain.InMemoryFavoritesRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { HttpClient(CIO) }
    single { InMemoryFavoritesRepository() } bind FavoritesRepository::class
}
