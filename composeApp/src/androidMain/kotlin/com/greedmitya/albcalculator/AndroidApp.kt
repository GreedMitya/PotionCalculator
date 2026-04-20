package com.greedmitya.albcalculator

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.core.os.LocaleListCompat
import com.greedmitya.albcalculator.domain.LanguagePreferenceRepository
import com.greedmitya.albcalculator.i18n.GameNameProvider
import com.greedmitya.albcalculator.i18n.LocalGameNameProvider
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AndroidApp() {
    val viewModel = koinViewModel<CraftViewModel>()
    val gameNameProvider = koinInject<GameNameProvider>()
    val languageRepo = koinInject<LanguagePreferenceRepository>()

    // Load the persisted language on first composition
    LaunchedEffect(Unit) {
        val savedLanguage = languageRepo.loadLanguage()
        gameNameProvider.loadForLanguage(savedLanguage)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(savedLanguage.bcp47Tag)
        )
    }

    CompositionLocalProvider(LocalGameNameProvider provides gameNameProvider) {
        MaterialTheme {
            AppNavigation(viewModel)
        }
    }
}
