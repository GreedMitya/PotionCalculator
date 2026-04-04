package com.greedmitya.albcalculator.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { HttpClient(CIO) }
}
