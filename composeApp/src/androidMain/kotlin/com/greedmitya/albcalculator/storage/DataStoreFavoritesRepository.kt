package com.greedmitya.albcalculator.storage

import android.content.Context
import com.greedmitya.albcalculator.domain.FavoritesRepository
import com.greedmitya.albcalculator.model.FavoriteRecipe

/**
 * Android DataStore-backed implementation of [FavoritesRepository].
 * Delegates to the existing [FavoritesStorage] static methods.
 */
class DataStoreFavoritesRepository(private val context: Context) : FavoritesRepository {

    override suspend fun loadFavorites(): List<FavoriteRecipe> =
        FavoritesStorage.loadFavorites(context)

    override suspend fun saveFavorites(favorites: List<FavoriteRecipe>) {
        FavoritesStorage.saveFavorites(context, favorites)
    }

    override suspend fun loadSelectedServer(): String =
        FavoritesStorage.loadSelectedServer(context)

    override suspend fun saveSelectedServer(server: String) {
        FavoritesStorage.saveSelectedServer(context, server)
    }

    override suspend fun loadSelectedLanguage(): String =
        FavoritesStorage.loadSelectedLanguage(context)

    override suspend fun saveSelectedLanguage(language: String) {
        FavoritesStorage.saveSelectedLanguage(context, language)
    }
}
