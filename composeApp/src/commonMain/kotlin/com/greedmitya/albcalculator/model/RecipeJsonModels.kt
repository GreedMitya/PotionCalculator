package com.greedmitya.albcalculator.model

import kotlinx.serialization.Serializable

/**
 * DTOs for deserializing recipes.json.
 * These are internal models — the rest of the app uses [Ingredient] and the Map types from [PotionRecipeRepository].
 */

@Serializable
internal data class RecipeDatabase(
    val potions: Map<String, PotionData>,
)

@Serializable
internal data class PotionData(
    val itemValues: Map<String, Int>,
    val recipes: Map<String, Map<String, List<IngredientDto>>>,
)

@Serializable
internal data class IngredientDto(
    val name: String,
    val quantity: Int,
)
