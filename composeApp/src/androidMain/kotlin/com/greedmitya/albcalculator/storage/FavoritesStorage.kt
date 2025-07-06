package com.greedmitya.albcalculator.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.greedmitya.albcalculator.model.FavoriteRecipe
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "favorites")

object FavoritesStorage {
    private val KEY = stringSetPreferencesKey("favorites_set")

    suspend fun saveFavorites(context: Context, list: List<FavoriteRecipe>) {
        val serialized = list.map { Json.encodeToString(it) }.toSet()
        context.dataStore.edit { prefs ->
            prefs[KEY] = serialized
        }
    }

    suspend fun loadFavorites(context: Context): List<FavoriteRecipe> {
        val prefs = context.dataStore.data.first()
        val raw = prefs[KEY] ?: return emptyList()
        return raw.mapNotNull {
            try {
                Json.decodeFromString<FavoriteRecipe>(it)
            } catch (e: Exception) { null }
        }
    }
}
