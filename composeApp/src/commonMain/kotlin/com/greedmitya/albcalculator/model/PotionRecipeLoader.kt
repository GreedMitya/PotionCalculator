package com.greedmitya.albcalculator.model

import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json

/**
 * Holds the parsed recipe data for the entire app lifetime.
 *
 * Must be initialized once at startup via [initializeWith] before any recipe data is accessed.
 * Called from [com.greedmitya.albcalculator.CraftViewModel]'s init block using
 * Compose Multiplatform's Res.readBytes("files/recipes.json").
 *
 * Returns empty maps until initialized — callers handle missing data gracefully (empty ingredient list).
 */
object PotionRecipeLoader {

    private val json = Json { ignoreUnknownKeys = true }

    @kotlin.concurrent.Volatile
    private var _ingredientsByTierAndEnchant: Map<String, Map<String, Map<Int, List<Ingredient>>>> = emptyMap()

    @kotlin.concurrent.Volatile
    private var _itemValues: Map<String, Map<String, Int>> = emptyMap()

    val ingredientsByTierAndEnchant: Map<String, Map<String, Map<Int, List<Ingredient>>>>
        get() = _ingredientsByTierAndEnchant

    val itemValues: Map<String, Map<String, Int>>
        get() = _itemValues

    /**
     * Parses the raw JSON string from recipes.json and populates the recipe maps.
     * Safe to call multiple times (idempotent if data doesn't change).
     */
    fun initializeWith(rawJson: String) {
        try {
            val db = json.decodeFromString<RecipeDatabase>(rawJson)
            _ingredientsByTierAndEnchant = buildIngredientMap(db)
            _itemValues = buildItemValuesMap(db)
            Napier.i("PotionRecipeLoader: loaded ${db.potions.size} potions from JSON")
        } catch (e: Exception) {
            Napier.e("PotionRecipeLoader: failed to parse recipes.json", e)
        }
    }

    private fun buildIngredientMap(
        db: RecipeDatabase,
    ): Map<String, Map<String, Map<Int, List<Ingredient>>>> =
        db.potions.mapValues { (_, potionData) ->
            potionData.recipes.mapValues { (_, enchantMap) ->
                enchantMap
                    .mapKeys { (key, _) -> key.toIntOrNull() ?: 0 }
                    .mapValues { (_, dtos) ->
                        dtos.map { Ingredient(name = it.name, quantity = it.quantity) }
                    }
            }
        }

    private fun buildItemValuesMap(
        db: RecipeDatabase,
    ): Map<String, Map<String, Int>> =
        db.potions.mapValues { (_, potionData) -> potionData.itemValues }
}
