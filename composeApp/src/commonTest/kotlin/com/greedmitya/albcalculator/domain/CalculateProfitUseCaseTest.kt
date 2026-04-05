package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.Ingredient
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CalculateProfitUseCaseTest {

    private companion object {
        const val DELTA = 0.01
    }

    private val useCase = CalculateProfitUseCase()

    @Test
    fun execute_mapsStringPricesToIngredients() {
        val ingredients = listOf(
            Ingredient(name = "HERB_A", quantity = 2, price = null),
            Ingredient(name = "HERB_B", quantity = 3, price = null),
        )
        val prices = mapOf(
            "HERB_A" to "100",
            "HERB_B" to "50",
        )

        val result = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "0",
            useFocus = false,
            isPremium = false,
            focusBasic = "",
            focusMastery = "",
            focusTotal = "",
            selectedCity = "Martlock",
            potionSellPrice = "0",
            outputQuantity = 5,
        )

        val expectedTotal = (100.0 * 2) + (50.0 * 3)
        assertEquals(expectedTotal, result.totalResources, DELTA)
    }

    @Test
    fun execute_withInvalidPriceStrings_treatsAsZero() {
        val ingredients = listOf(
            Ingredient(name = "HERB_A", quantity = 2, price = null),
        )
        val prices = mapOf(
            "HERB_A" to "not_a_number",
        )

        val result = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "abc",
            useFocus = false,
            isPremium = false,
            focusBasic = "",
            focusMastery = "",
            focusTotal = "",
            selectedCity = "Martlock",
            potionSellPrice = "xyz",
            outputQuantity = 5,
        )

        assertEquals(0.0, result.totalResources, DELTA)
        assertEquals(0.0, result.profitSilver, DELTA)
    }

    @Test
    fun execute_withMissingIngredientPrice_treatsAsZero() {
        val ingredients = listOf(
            Ingredient(name = "HERB_A", quantity = 2, price = null),
        )
        val prices = emptyMap<String, String>()

        val result = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "0",
            useFocus = false,
            isPremium = false,
            focusBasic = "",
            focusMastery = "",
            focusTotal = "",
            selectedCity = "Martlock",
            potionSellPrice = "500",
            outputQuantity = 1,
        )

        assertEquals(0.0, result.totalResources, DELTA)
        assertTrue(result.profitSilver > 0, "Should still have profit from sell price")
    }

    @Test
    fun execute_passesOutputQuantityCorrectly() {
        val ingredients = listOf(
            Ingredient(name = "HERB_A", quantity = 10, price = null),
        )
        val prices = mapOf("HERB_A" to "100")

        val resultFive = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "0",
            useFocus = false,
            isPremium = false,
            focusBasic = "",
            focusMastery = "",
            focusTotal = "",
            selectedCity = "Martlock",
            potionSellPrice = "0",
            outputQuantity = 5,
        )

        val resultTen = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "0",
            useFocus = false,
            isPremium = false,
            focusBasic = "",
            focusMastery = "",
            focusTotal = "",
            selectedCity = "Martlock",
            potionSellPrice = "0",
            outputQuantity = 10,
        )

        // finalCost per item should be halved with double output
        assertEquals(resultFive.finalCost, resultTen.finalCost * 2, DELTA)
    }
}
