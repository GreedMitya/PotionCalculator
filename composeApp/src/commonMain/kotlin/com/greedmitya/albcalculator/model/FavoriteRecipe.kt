package com.greedmitya.albcalculator.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteRecipe(
    val potionName: String,
    val tier: String,
    val enchantment: Int,
    val city: String,
    /** Fee for 100 nutrition saved at time of save. Empty string means not set. */
    val feePerNutrition: String = "",
    /** Ingredient prices keyed by ingredient ID, saved at time of save. */
    val ingredientPrices: Map<String, String> = emptyMap(),
)
