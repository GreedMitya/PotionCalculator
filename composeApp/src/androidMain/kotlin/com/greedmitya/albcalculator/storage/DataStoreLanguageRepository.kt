package com.greedmitya.albcalculator.storage

import android.content.Context
import com.greedmitya.albcalculator.domain.LanguagePreferenceRepository
import com.greedmitya.albcalculator.i18n.AppLanguage

/**
 * Android DataStore-backed implementation of [LanguagePreferenceRepository].
 * Delegates to [FavoritesStorage] which owns the shared "favorites" DataStore.
 */
class DataStoreLanguageRepository(private val context: Context) : LanguagePreferenceRepository {

    override suspend fun loadLanguage(): AppLanguage =
        AppLanguage.fromCode(FavoritesStorage.loadLanguageCode(context))

    override suspend fun saveLanguage(language: AppLanguage) {
        FavoritesStorage.saveLanguageCode(context, language.code)
    }
}
