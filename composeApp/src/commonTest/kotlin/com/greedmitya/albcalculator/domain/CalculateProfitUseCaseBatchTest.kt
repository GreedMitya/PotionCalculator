package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.Ingredient
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateProfitUseCaseBatchTest {

    private companion object {
        const val DELTA = 0.01
    }

    private val useCase = CalculateProfitUseCase()

    @Test
    fun execute_withCraftQuantity_passesThroughToResult() {
        val ingredients = listOf(
            Ingredient(name = "HERB_A", quantity = 2, price = null),
        )
        val prices = mapOf("HERB_A" to "100")

        val result = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "50",
            useFocus = false,
            isPremium = false,
            availableFocus = "",

            enchantLevel = 0,
            selectedCity = "Martlock",
            potionSellPrice = "500",
            outputQuantity = 5,
            craftQuantity = 7,
        )

        assertEquals(7, result.craftQuantity)
    }

    @Test
    fun execute_defaultCraftQuantity_isOne() {
        val ingredients = listOf(
            Ingredient(name = "HERB_A", quantity = 2, price = null),
        )
        val prices = mapOf("HERB_A" to "100")

        val result = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "0",
            useFocus = false,
            isPremium = false,
            availableFocus = "",

            enchantLevel = 0,
            selectedCity = "Martlock",
            potionSellPrice = "0",
            outputQuantity = 5,
        )

        assertEquals(1, result.craftQuantity)
    }

    @Test
    fun execute_craftQuantityDoesNotChangeProfitPerItem() {
        val ingredients = listOf(
            Ingredient(name = "HERB_A", quantity = 4, price = null),
        )
        val prices = mapOf("HERB_A" to "100")

        val resultSingle = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "50",
            useFocus = false,
            isPremium = false,
            availableFocus = "",

            enchantLevel = 0,
            selectedCity = "Martlock",
            potionSellPrice = "600",
            outputQuantity = 5,
            craftQuantity = 1,
        )

        val resultTen = useCase.execute(
            ingredients = ingredients,
            ingredientPrices = prices,
            baseId = null,
            selectedTier = null,
            feePerNutritionInput = "50",
            useFocus = false,
            isPremium = false,
            availableFocus = "",

            enchantLevel = 0,
            selectedCity = "Martlock",
            potionSellPrice = "600",
            outputQuantity = 5,
            craftQuantity = 10,
        )

        // Per-item profit should be identical
        assertEquals(resultSingle.profitSilver, resultTen.profitSilver, DELTA)
        assertEquals(resultSingle.finalCost, resultTen.finalCost, DELTA)

        // Total profit = profitPerItem × craftQuantity(batches) × outputQuantity(items per batch)
        assertEquals(resultSingle.profitSilver * 10 * 5, resultTen.totalProfitSilver, DELTA)
    }
}
