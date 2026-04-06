package com.greedmitya.albcalculator

import android.app.Application
import android.content.pm.ApplicationInfo
import com.greedmitya.albcalculator.di.appModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application-level initialization for Koin DI and logging.
 *
 * Koin MUST be started here (not in Activity.onCreate) to avoid
 * KoinApplicationAlreadyStartedException on Activity recreation.
 */
class PotionCalculatorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Only enable verbose logging in debug builds
        val isDebug = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        if (isDebug) {
            Napier.base(DebugAntilog())
        }

        startKoin {
            androidContext(this@PotionCalculatorApp)
            modules(appModule)
        }
    }
}
