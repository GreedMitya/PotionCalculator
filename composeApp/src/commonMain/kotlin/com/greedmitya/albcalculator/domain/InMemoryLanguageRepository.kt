package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.i18n.AppLanguage

/**
 * In-memory stub used for iOS/Desktop targets where DataStore is not available.
 * Language resets to English on every app restart.
 */
class InMemoryLanguageRepository : LanguagePreferenceRepository {
    private var language: AppLanguage = AppLanguage.DEFAULT

    override suspend fun loadLanguage(): AppLanguage = language

    override suspend fun saveLanguage(language: AppLanguage) {
        this.language = language
    }
}
