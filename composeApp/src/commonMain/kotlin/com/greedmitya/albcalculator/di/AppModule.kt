package com.greedmitya.albcalculator.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val appModule = module {
    single { HttpClient(CIO) }
}
