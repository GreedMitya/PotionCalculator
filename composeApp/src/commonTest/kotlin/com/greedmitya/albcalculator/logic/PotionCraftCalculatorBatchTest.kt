package com.greedmitya.albcalculator.logic

import com.greedmitya.albcalculator.model.Ingredient
import kotlin.test.Test
import kotlin.test.assertEquals

class PotionCraftCalculatorBatchTest {

    private companion object {
        const val DELTA = 0.01
    }

    @Test
    fun calculate_withCraftQuantity_storesQuantityInResult() {
        val result = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 2, price = 100.0)),
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 500.0,
            outputQuantity = 5,
            craftQuantity = 10,
        )

        assertEquals(10, result.craftQuantity)
    }

    @Test
    fun calculate_defaultCraftQuantity_isOne() {
        val result = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 2, price = 100.0)),
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 500.0,
            outputQuantity = 5,
        )

        assertEquals(1, result.craftQuantity)
    }

    @Test
    fun calculate_craftQuantityDoesNotAffectPerItemCost() {
        val resultSingle = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 4, price = 100.0)),
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
            craftQuantity = 1,
        )

        val resultTen = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 4, price = 100.0)),
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
            craftQuantity = 10,
        )

        // Per-item values should be identical regardless of craftQuantity
        assertEquals(resultSingle.finalCost, resultTen.finalCost, DELTA)
        assertEquals(resultSingle.profitSilver, resultTen.profitSilver, DELTA)
        assertEquals(resultSingle.totalResources, resultTen.totalResources, DELTA)
        assertEquals(resultSingle.withPlacementFee, resultTen.withPlacementFee, DELTA)
    }

    @Test
    fun calculate_craftQuantity_totalProfitScalesLinearly() {
        val result = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 4, price = 100.0)),
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
            craftQuantity = 5,
        )

        // totalProfitSilver = profitSilver * craftQuantity(batches) * outputQuantity(items per batch)
        assertEquals(result.profitSilver * 5 * 5, result.totalProfitSilver, DELTA)
    }

    @Test
    fun calculate_craftQuantity_totalCostScalesLinearly() {
        val result = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 4, price = 100.0)),
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
            craftQuantity = 8,
        )

        // totalCostSilver = finalCost * craftQuantity(batches) * outputQuantity(items per batch)
        assertEquals(result.finalCost * 8 * 5, result.totalCostSilver, DELTA)
    }
}
