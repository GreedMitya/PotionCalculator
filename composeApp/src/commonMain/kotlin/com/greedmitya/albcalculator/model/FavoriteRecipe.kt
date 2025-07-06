package com.greedmitya.albcalculator.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteRecipe(
    val potionName: String,
    val tier: String,
    val enchantment: Int,
    val city: String
)



