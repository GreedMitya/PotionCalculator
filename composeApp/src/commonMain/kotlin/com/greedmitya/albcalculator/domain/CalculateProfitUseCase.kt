package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.Ingredient
import com.greedmitya.albcalculator.logic.PotionCraftCalculator
import com.greedmitya.albcalculator.model.PotionCraftResult
import com.greedmitya.albcalculator.model.potionItemValues

class CalculateProfitUseCase {

    fun execute(
        ingredients: List<Ingredient>,
        ingredientPrices: Map<String, String>,
        baseId: String?,
        selectedTier: String?,
        feePerNutritionInput: String,
        useFocus: Boolean,
        isPremium: Boolean,
        focusBasic: String,
        focusMastery: String,
        focusTotal: String,
        selectedCity: String?,
        potionSellPrice: String,
        outputQuantity: Int
    ): PotionCraftResult {

        val ingredientsWithPrices = ingredients.map {
            val price = ingredientPrices[it.name]?.toDoubleOrNull()
            it.copy(price = price)
        }

        val itemValue = baseId?.let { id ->
            potionItemValues[id]?.get(selectedTier ?: "")
        } ?: 0

        return PotionCraftCalculator.calculate(
            ingredients = ingredientsWithPrices,
            feePerNutrition = feePerNutritionInput.toDoubleOrNull() ?: 0.0,
            useFocus = useFocus,
            isPremium = isPremium,
            focusBasic = focusBasic.toDoubleOrNull(),
            focusMastery = focusMastery.toDoubleOrNull(),
            focusTotal = focusTotal.toDoubleOrNull(),
            itemValue = itemValue,
            city = selectedCity,
            sellPrice = potionSellPrice.toDoubleOrNull(),
            outputQuantity = outputQuantity
        )
    }
}
