package com.greedmitya.albcalculator.i18n

import kotlinx.coroutines.flow.StateFlow

/**
 * Resolves Albion Online game item names in the active language.
 *
 * Game names must match the official Albion Online client translations so that
 * copy-to-clipboard produces strings the user can paste directly into the game
 * search bar. English is always the fallback when a translation is missing.
 */
interface GameNameProvider {
    /** Returns the localized display name for an ingredient item ID (e.g. "T2_AGARIC" → "Arcane Agaric"). */
    fun getIngredientName(itemId: String): String

    /** Returns the localized potion name for an English potion display name (e.g. "Healing Potion"). */
    fun getPotionDisplayName(englishName: String): String

    /** Returns the localized city name for an English city name (e.g. "Caerleon"). */
    fun getCityName(englishName: String): String

    /** Loads or reloads translations for the given language. Call when the user changes language. */
    suspend fun loadForLanguage(language: AppLanguage)

    /** The language currently loaded. */
    val currentLanguage: AppLanguage

    /**
     * Emits the active language every time [loadForLanguage] completes.
     * Observe this in the UI to trigger recomposition after a language change.
     */
    val languageChanges: StateFlow<AppLanguage>
}
