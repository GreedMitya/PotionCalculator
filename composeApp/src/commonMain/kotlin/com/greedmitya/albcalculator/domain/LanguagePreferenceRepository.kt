package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.i18n.AppLanguage

/**
 * Persists the user's selected app language across sessions.
 * Platform-specific implementations live in androidMain.
 */
interface LanguagePreferenceRepository {
    suspend fun loadLanguage(): AppLanguage
    suspend fun saveLanguage(language: AppLanguage)
}
