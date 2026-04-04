package com.greedmitya.albcalculator.logic

import com.greedmitya.albcalculator.model.Ingredient
import kotlin.test.Test
import kotlin.test.assertEquals
import com.greedmitya.albcalculator.model.PotionCraftResult
import kotlin.test.assertTrue

class PotionCraftCalculatorTest {

    @Test
    fun testBasicCalculationWithoutPremiumOrFocus() {
        val ingredients = listOf(
            Ingredient("Common Herb", 2, 100.0),
            Ingredient("Cheap Liquid", 3, 50.0)
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            focusBasic = null,
            focusMastery = null,
            focusTotal = null,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5
        )

        val expectedResources = 100.0 * 2 + 50.0 * 3
        assertEquals(expectedResources, result.totalResources, 0.01)
        assertTrue(result.finalCost > 0)
        assertTrue(result.profitSilver != 0.0)
    }

    @Test
    fun testWithRareIngredientsAndBrecilienPremium() {
        val ingredients = listOf(
            Ingredient("RARE Crystal", 1, 300.0),
            Ingredient("RARE Root", 1, 120.0),
            Ingredient("Normal Herb", 2, 100.0)
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 80.0,
            useFocus = true,
            isPremium = true,
            focusBasic = 10.0,
            focusMastery = 5.0,
            focusTotal = 15.0,
            itemValue = 36,
            city = "Brecilien",
            sellPrice = 950.0,
            outputQuantity = 5
        )

        assertTrue(result.finalCost > 0)
        assertTrue(result.profitSilver != 0.0)
        assertTrue(result.totalResources > 0)
    }
}
