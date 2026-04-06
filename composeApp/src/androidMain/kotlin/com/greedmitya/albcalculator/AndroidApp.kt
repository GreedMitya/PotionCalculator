package com.greedmitya.albcalculator

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.CompositionLocalProvider
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale

@Composable
fun AndroidApp() {
    val viewModel = koinViewModel<CraftViewModel>()

    val langCode = viewModel.languageDisplayNames.entries
        .firstOrNull { it.value == viewModel.selectedLanguage }?.key ?: "en"

    val baseContext = LocalContext.current
    val localizedContext = remember(langCode) {
        val config = Configuration(baseContext.resources.configuration)
        config.setLocale(Locale(langCode))
        baseContext.createConfigurationContext(config)
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides localizedContext.resources.configuration,
    ) {
        MaterialTheme {
            AppNavigation(viewModel)
        }
    }
}
