package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.FavoriteRecipe

/**
 * In-memory fallback for platforms without persistent storage (iOS, Desktop stubs).
 * Data does not survive app restarts.
 */
class InMemoryFavoritesRepository : FavoritesRepository {
    private val favorites = mutableListOf<FavoriteRecipe>()
    private var selectedServer = "Europe"
    private var selectedLanguage = "English"

    override suspend fun loadFavorites(): List<FavoriteRecipe> = favorites.toList()

    override suspend fun saveFavorites(favorites: List<FavoriteRecipe>) {
        this.favorites.clear()
        this.favorites.addAll(favorites)
    }

    override suspend fun loadSelectedServer(): String = selectedServer

    override suspend fun saveSelectedServer(server: String) {
        selectedServer = server
    }

    override suspend fun loadSelectedLanguage(): String = selectedLanguage

    override suspend fun saveSelectedLanguage(language: String) {
        selectedLanguage = language
    }
}
