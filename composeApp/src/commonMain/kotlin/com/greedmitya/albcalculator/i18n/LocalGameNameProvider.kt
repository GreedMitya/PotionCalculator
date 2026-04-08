package com.greedmitya.albcalculator.i18n

import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocal that provides the active [GameNameProvider] to all composables.
 *
 * Must be provided at the app root (AndroidApp.kt) via CompositionLocalProvider.
 * Composables read it via: LocalGameNameProvider.current
 */
val LocalGameNameProvider = compositionLocalOf<GameNameProvider> {
    error("No GameNameProvider provided — wrap your composable tree with CompositionLocalProvider(LocalGameNameProvider provides ...)")
}
