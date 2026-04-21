package com.greedmitya.albcalculator.logic

import com.greedmitya.albcalculator.model.Ingredient
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PotionCraftCalculatorTest {

    // ── Helper constants matching PotionCraftCalculator internals ──
    private companion object {
        // Reference the calculator's own internal constants to avoid precision drift.
        val BRECILIEN_RETURN_RATE = PotionCraftCalculator.BRECILIEN_RETURN_RATE
        val DEFAULT_RETURN_RATE = PotionCraftCalculator.DEFAULT_RETURN_RATE
        const val CRAFTING_TAX_MULTIPLIER = 0.001125
        const val PREMIUM_TAX_RATE = 0.065
        const val STANDARD_TAX_RATE = 0.105
        const val DELTA = 0.01
    }

    // ── Basic Calculation Tests ──

    @Test
    fun calculate_withRegularIngredients_computesCorrectTotalResources() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 2, price = 100.0),
            Ingredient(name = "Liquid", quantity = 3, price = 50.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
        )

        val expectedTotalResources = (100.0 * 2) + (50.0 * 3) // 350.0
        assertEquals(expectedTotalResources, result.totalResources, DELTA)
    }

    @Test
    fun calculate_withDefaultCity_appliesDefaultReturnRate() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 4, price = 100.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = null,
            outputQuantity = 5,
        )

        // regularRawCost = 400.0, returnRate = 0.152, afterReturn = 400 * 0.848 = 339.2
        val expectedWithPlacement = 400.0 * (1 - DEFAULT_RETURN_RATE)
        assertEquals(expectedWithPlacement, result.withPlacementFee, DELTA)
    }

    @Test
    fun calculate_withBrecilien_appliesHigherReturnRate() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 4, price = 100.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Brecilien",
            sellPrice = null,
            outputQuantity = 5,
        )

        // regularRawCost = 400.0, returnRate = 0.248, afterReturn = 400 * 0.752 = 300.8
        val expectedWithPlacement = 400.0 * (1 - BRECILIEN_RETURN_RATE)
        assertEquals(expectedWithPlacement, result.withPlacementFee, DELTA)

        // Brecilien return is higher, so cost should be lower than default city
        val defaultResult = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = null,
            outputQuantity = 5,
        )
        assertTrue(result.withPlacementFee < defaultResult.withPlacementFee)
    }

    // ── Rare Ingredient Tests ──

    @Test
    fun calculate_withRareIngredients_doesNotApplyReturnRate() {
        val rareOnly = listOf(
            Ingredient(name = "RARE Crystal", quantity = 1, price = 500.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = rareOnly,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Brecilien",
            sellPrice = null,
            outputQuantity = 1,
        )

        // Rare ingredients get NO return rate — full cost
        assertEquals(500.0, result.totalResources, DELTA)
        assertEquals(500.0, result.withPlacementFee, DELTA)
    }

    @Test
    fun calculate_withMixedIngredients_separatesRareAndRegularCorrectly() {
        val ingredients = listOf(
            Ingredient(name = "RARE Root", quantity = 1, price = 300.0),
            Ingredient(name = "Normal Herb", quantity = 2, price = 100.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = null,
            outputQuantity = 1,
        )

        val rareCost = 300.0 // no return rate
        val regularAfterReturn = 200.0 * (1 - DEFAULT_RETURN_RATE) // 200 * 0.848 = 169.6
        val expectedTotal = 300.0 + 200.0
        val expectedWithPlacement = rareCost + regularAfterReturn

        assertEquals(expectedTotal, result.totalResources, DELTA)
        assertEquals(expectedWithPlacement, result.withPlacementFee, DELTA)
    }

    @Test
    fun calculate_rareDetection_isCaseInsensitive() {
        val ingredients = listOf(
            Ingredient(name = "rare_item", quantity = 1, price = 200.0),
            Ingredient(name = "Rare_Extract", quantity = 1, price = 150.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = null,
            outputQuantity = 1,
        )

        // Both should be treated as rare → no return rate
        val expectedTotal = 200.0 + 150.0
        assertEquals(expectedTotal, result.withPlacementFee, DELTA)
    }

    // ── Tax & Premium Tests ──

    @Test
    fun calculate_withPremium_appliesLowerTaxRate() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 1, price = 0.0),
        )

        val premiumResult = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = true,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 1000.0,
            outputQuantity = 1,
        )

        val standardResult = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 1000.0,
            outputQuantity = 1,
        )

        // Premium: netSell = 1000 * (1 - 0.065) = 935.0
        // Standard: netSell = 1000 * (1 - 0.105) = 895.0
        val expectedPremiumNet = 1000.0 * (1 - PREMIUM_TAX_RATE)
        val expectedStandardNet = 1000.0 * (1 - STANDARD_TAX_RATE)

        assertEquals(expectedPremiumNet, premiumResult.profitSilver, DELTA)
        assertEquals(expectedStandardNet, standardResult.profitSilver, DELTA)
        assertTrue(premiumResult.profitSilver > standardResult.profitSilver)
    }

    // ── Crafting Tax Tests ──

    @Test
    fun calculate_craftingTax_computedCorrectly() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 1, price = 0.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 100.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 48,
            city = "Martlock",
            sellPrice = null,
            outputQuantity = 5,
        )

        // craftingTax = 100.0 * 48 * 0.001125 = 5.4
        // craftingTaxPerItem = 5.4 / 5 = 1.08
        val expectedTax = 100.0 * 48 * CRAFTING_TAX_MULTIPLIER
        val expectedTaxPerItem = expectedTax / 5
        assertEquals(expectedTaxPerItem, result.finalCost, DELTA)
    }

    // ── Output Quantity Tests ──

    @Test
    fun calculate_withHighOutputQuantity_dividesCorrectly() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 10, price = 100.0),
        )

        val resultSingle = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 200.0,
            outputQuantity = 1,
        )

        val resultTen = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 200.0,
            outputQuantity = 10,
        )

        // Cost per item should be 10x lower with 10 output vs 1
        assertEquals(resultSingle.finalCost, resultTen.finalCost * 10, DELTA)
    }

    // ── Edge Cases ──

    @Test
    fun calculate_withZeroIngredientPrice_returnsZeroCost() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 5, price = 0.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 0.0,
            outputQuantity = 5,
        )

        assertEquals(0.0, result.totalResources, DELTA)
        assertEquals(0.0, result.finalCost, DELTA)
        assertEquals(0.0, result.profitSilver, DELTA)
    }

    @Test
    fun calculate_withNullIngredientPrice_treatsAsZero() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 5, price = null),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 100.0,
            outputQuantity = 5,
        )

        assertEquals(0.0, result.totalResources, DELTA)
    }

    @Test
    fun calculate_withNullSellPrice_treatsAsZero() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 1, price = 100.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = null,
            outputQuantity = 1,
        )

        // netSell = 0, profit = 0 - cost = negative
        assertTrue(result.profitSilver < 0)
    }

    @Test
    fun calculate_withNullCity_usesDefaultReturnRate() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 4, price = 100.0),
        )

        val resultNullCity = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = null,
            sellPrice = null,
            outputQuantity = 5,
        )

        val resultMartlock = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = null,
            outputQuantity = 5,
        )

        // null city should use same default return rate as Martlock
        assertEquals(resultMartlock.withPlacementFee, resultNullCity.withPlacementFee, DELTA)
    }

    @Test
    fun calculate_withEmptyIngredients_returnsZeroCosts() {
        val result = PotionCraftCalculator.calculate(
            ingredients = emptyList(),
            feePerNutrition = 50.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 24,
            city = "Martlock",
            sellPrice = 500.0,
            outputQuantity = 5,
        )

        assertEquals(0.0, result.totalResources, DELTA)
        // Should still have crafting tax
        val expectedTaxPerItem = (50.0 * 24 * CRAFTING_TAX_MULTIPLIER) / 5
        assertEquals(expectedTaxPerItem, result.finalCost, DELTA)
    }

    // ── Profit Percent (PotionCraftResult) Tests ──

    @Test
    fun profitPercent_withZeroFinalCost_returnsNull() {
        val ingredients = listOf(
            Ingredient(name = "Herb", quantity = 1, price = 0.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 100.0,
            outputQuantity = 1,
        )

        // finalCost = 0, so profitPercent should be null (avoid division by zero)
        assertNull(result.profitPercent)
    }

    // ── Full Integration-Like Test ──

    @Test
    fun calculate_fullScenario_computesExactValues() {
        val ingredients = listOf(
            Ingredient(name = "RARE Crystal", quantity = 1, price = 300.0),
            Ingredient(name = "Normal Herb", quantity = 2, price = 100.0),
        )

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 80.0,
            useFocus = false,
            isPremium = true,
            availableFocus = null,

            focusCostPerBatch = 0,
            itemValue = 36,
            city = "Brecilien",
            sellPrice = 950.0,
            outputQuantity = 5,
        )

        // Step-by-step verification:
        // rareCost = 300.0
        // regularRawCost = 200.0
        // returnRate = 0.33/1.33 ≈ 0.24812 (Brecilien)
        // regularAfterReturn = 200.0 * (1 - 0.24812) ≈ 150.376
        // totalCostAfterReturn ≈ 300.0 + 150.376 = 450.376
        // craftingTax = 80.0 * 36 * 0.001125 = 3.24
        // craftingTaxPerItem = 3.24 / 5 = 0.648
        // costPerItem ≈ (450.376 / 5) + 0.648 ≈ 90.723
        // taxRate = 0.065 (premium)
        // netSell = 950.0 * 0.935 = 888.25
        // profitSilver ≈ 888.25 - 90.723 ≈ 797.527
        val expectedWithFee = 200.0 * (1 - BRECILIEN_RETURN_RATE) + 300.0
        val expectedCostPerItem = expectedWithFee / 5 + (80.0 * 36 * CRAFTING_TAX_MULTIPLIER / 5)
        val expectedProfit = 950.0 * (1 - PREMIUM_TAX_RATE) - expectedCostPerItem

        assertEquals(500.0, result.totalResources, DELTA)
        assertEquals(expectedWithFee, result.withPlacementFee, DELTA)
        assertEquals(expectedCostPerItem, result.finalCost, DELTA)
        assertEquals(950.0, result.estimatedSellPrice)
        assertEquals(expectedProfit, result.profitSilver, DELTA)
    }

    // ── Focus Return Rate Tests ──

    @Test
    fun calculate_noFocus_brecilien_appliesBaseReturnRate() {
        val ingredients = listOf(Ingredient(name = "Herb", quantity = 10, price = 100.0))
        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = false,
            isPremium = false,
            availableFocus = null,
            focusCostPerBatch = 0,
            itemValue = 0,
            city = "Brecilien",
            sellPrice = null,
            outputQuantity = 5,
        )
        // regularRawCost = 1000, returnRate = 24.8%, afterReturn = 752.0
        assertEquals(PotionCraftCalculator.BRECILIEN_RETURN_RATE, result.effectiveReturnRate, DELTA)
        assertEquals(0, result.batchesWithFocus)
    }

    @Test
    fun calculate_withFocus_brecilien_enoughFocus_applies47_4PercentReturn() {
        val ingredients = listOf(Ingredient(name = "Herb", quantity = 10, price = 100.0))
        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = true,
            isPremium = false,
            availableFocus = 1000.0,
            focusCostPerBatch = 210,
            itemValue = 0,
            city = "Brecilien",
            sellPrice = null,
            outputQuantity = 5,
            craftQuantity = 4,
        )
        // 1000 / 210 = 4 batches with focus — covers all 4
        assertEquals(PotionCraftCalculator.BRECILIEN_FOCUS_RETURN_RATE, result.effectiveReturnRate, DELTA)
        assertEquals(4, result.batchesWithFocus)
    }

    @Test
    fun calculate_withFocus_defaultCity_enoughFocus_applies42_9PercentReturn() {
        val ingredients = listOf(Ingredient(name = "Herb", quantity = 10, price = 100.0))
        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = true,
            isPremium = false,
            availableFocus = 10000.0,
            focusCostPerBatch = 210,
            itemValue = 0,
            city = "Martlock",
            sellPrice = null,
            outputQuantity = 5,
        )
        assertEquals(PotionCraftCalculator.DEFAULT_FOCUS_RETURN_RATE, result.effectiveReturnRate, DELTA)
    }

    @Test
    fun calculate_withFocus_focusRunsOutHalfway_returnsBlendedRate() {
        val ingredients = listOf(Ingredient(name = "Herb", quantity = 10, price = 100.0))
        // 2 batches planned, focus for exactly 1 batch
        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = true,
            isPremium = false,
            availableFocus = 210.0,
            focusCostPerBatch = 210,
            itemValue = 0,
            city = "Brecilien",
            sellPrice = null,
            outputQuantity = 5,
            craftQuantity = 2,
        )
        // blended = (1 * 0.474 + 1 * 0.248) / 2 = 0.361
        val expected = (PotionCraftCalculator.BRECILIEN_FOCUS_RETURN_RATE + PotionCraftCalculator.BRECILIEN_RETURN_RATE) / 2
        assertEquals(expected, result.effectiveReturnRate, DELTA)
        assertEquals(1, result.batchesWithFocus)
    }

    @Test
    fun calculate_withFocus_noAvailableFocus_treatsAllBatchesAsFocused() {
        val ingredients = listOf(Ingredient(name = "Herb", quantity = 10, price = 100.0))
        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = 0.0,
            useFocus = true,
            isPremium = false,
            availableFocus = null,
            focusCostPerBatch = 210,
            itemValue = 0,
            city = "Brecilien",
            sellPrice = null,
            outputQuantity = 5,
            craftQuantity = 3,
        )
        // null availableFocus = user didn't specify = assume all batches get focus
        assertEquals(PotionCraftCalculator.BRECILIEN_FOCUS_RETURN_RATE, result.effectiveReturnRate, DELTA)
        assertEquals(3, result.batchesWithFocus)
    }
}
