package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.FavoriteRecipe

/**
 * Abstraction for favorites persistence.
 * Allows the ViewModel to manage favorites without knowing about Android DataStore.
 * Platform-specific implementations live in androidMain / iosMain.
 */
interface FavoritesRepository {
    suspend fun loadFavorites(): List<FavoriteRecipe>
    suspend fun saveFavorites(favorites: List<FavoriteRecipe>)
    suspend fun loadSelectedServer(): String
    suspend fun saveSelectedServer(server: String)
    suspend fun loadSelectedLanguage(): String
    suspend fun saveSelectedLanguage(language: String)
}
