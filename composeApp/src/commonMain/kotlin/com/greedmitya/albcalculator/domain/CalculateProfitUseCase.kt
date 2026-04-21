package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.Ingredient
import com.greedmitya.albcalculator.logic.PotionCraftCalculator
import com.greedmitya.albcalculator.model.PotionCraftResult
import com.greedmitya.albcalculator.model.PotionRecipeLoader
import com.greedmitya.albcalculator.model.potionItemValues

class CalculateProfitUseCase {

    fun execute(
        ingredients: List<Ingredient>,
        ingredientPrices: Map<String, String>,
        baseId: String?,
        selectedTier: String?,
        enchantLevel: Int,
        feePerNutritionInput: String,
        useFocus: Boolean,
        isPremium: Boolean,
        availableFocus: String,
        basicSpecLevel: String = "",
        masteryLevel: String = "",
        selectedCity: String?,
        potionSellPrice: String,
        outputQuantity: Int,
        craftQuantity: Int = 1,
    ): PotionCraftResult {

        val ingredientsWithPrices = ingredients.map {
            val price = ingredientPrices[it.name]?.toDoubleOrNull()
            it.copy(price = price)
        }

        val itemValue = baseId?.let { id ->
            potionItemValues[id]?.get(selectedTier ?: "")
        } ?: 0

        val focusCostPerBatch = if (baseId != null && selectedTier != null)
            PotionRecipeLoader.focusCost(baseId, selectedTier, enchantLevel)
        else 0

        return PotionCraftCalculator.calculate(
            ingredients = ingredientsWithPrices,
            feePerNutrition = feePerNutritionInput.toDoubleOrNull() ?: 0.0,
            useFocus = useFocus,
            isPremium = isPremium,
            availableFocus = availableFocus.toDoubleOrNull(),
            focusCostPerBatch = focusCostPerBatch,
            itemValue = itemValue,
            city = selectedCity,
            sellPrice = potionSellPrice.toDoubleOrNull(),
            outputQuantity = outputQuantity,
            craftQuantity = craftQuantity,
            basicSpecLevel = basicSpecLevel.toDoubleOrNull() ?: 0.0,
            masteryLevel = masteryLevel.toDoubleOrNull() ?: 0.0,
        )
    }
}
