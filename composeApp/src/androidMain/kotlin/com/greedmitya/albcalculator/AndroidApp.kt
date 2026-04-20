package com.greedmitya.albcalculator

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    // Each time loadForLanguage() completes, languageChanges emits a new value.
    // remember(currentLanguage) creates a new wrapper object — its changed reference
    // tells CompositionLocalProvider to recompose all consumers instantly.
    val currentLanguage by gameNameProvider.languageChanges.collectAsState()
    val activeProvider = remember(currentLanguage) { object : GameNameProvider by gameNameProvider {} }

    CompositionLocalProvider(LocalGameNameProvider provides activeProvider) {
        MaterialTheme {
            AppNavigation(viewModel)
        }
    }
}
