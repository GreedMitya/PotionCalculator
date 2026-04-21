package com.greedmitya.albcalculator.logic

import com.greedmitya.albcalculator.model.Ingredient
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PotionCraftCalculatorFocusReductionTest {

    private companion object {
        const val DELTA = 0.5 // rounding to nearest Int, so 0.5 is sufficient tolerance
    }

    // ── reduceFocusCost unit tests ──

    @Test
    fun reduceFocusCost_zeroSpecialization_returnsBaseCost() {
        val result = PotionCraftCalculator.reduceFocusCost(
            baseCost = 210,
            basicSpecLevel = 0.0,
            masteryLevel = 0.0,
        )
        assertEquals(210, result)
    }

    @Test
    fun reduceFocusCost_zeroCost_returnsZero() {
        val result = PotionCraftCalculator.reduceFocusCost(
            baseCost = 0,
            basicSpecLevel = 100.0,
            masteryLevel = 100.0,
        )
        assertEquals(0, result)
    }

    @Test
    fun reduceFocusCost_100Mastery_reducesToAbout14Percent() {
        // 100 mastery × 280 = 28000 pts → 0.5^(28000/10000) = 0.5^2.8 ≈ 0.1435
        // 210 × 0.1435 ≈ 30.1 → rounds to 30
        val result = PotionCraftCalculator.reduceFocusCost(
            baseCost = 210,
            basicSpecLevel = 0.0,
            masteryLevel = 100.0,
        )
        assertTrue(result in 28..32, "Expected ~30 for 100 mastery, got $result")
    }

    @Test
    fun reduceFocusCost_withBasicAndMastery_appliesCombined() {
        // 50 basic × 30 + 50 mastery × 280 = 1500 + 14000 = 15500 pts
        // 0.5^(15500/10000) = 0.5^1.55 ≈ 0.3408
        // 1000 × 0.3408 ≈ 341
        val result = PotionCraftCalculator.reduceFocusCost(
            baseCost = 1000,
            basicSpecLevel = 50.0,
            masteryLevel = 50.0,
        )
        assertTrue(result in 330..355, "Expected ~341 for basic=50 mastery=50, got $result")
    }

    @Test
    fun reduceFocusCost_onlyBasicSpec_reducesModestly() {
        // 100 basic × 30 = 3000 pts → 0.5^0.3 ≈ 0.812
        // 210 × 0.812 ≈ 171
        val result = PotionCraftCalculator.reduceFocusCost(
            baseCost = 210,
            basicSpecLevel = 100.0,
            masteryLevel = 0.0,
        )
        assertTrue(result in 165..178, "Expected ~171 for basic=100, got $result")
    }

    // ── Integration: reduced cost affects batchesWithFocus ──

    @Test
    fun calculate_withMastery_usesReducedCostForBatchCount() {
        // Base focus cost 210 per batch, 100 mastery → reduced ≈ 30 pts/batch
        // Available focus = 210: without mastery → 1 batch; with 100 mastery → ~7 batches
        val resultNoMastery = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 4, price = 100.0)),
            feePerNutrition = 0.0,
            useFocus = true,
            isPremium = false,
            availableFocus = 210.0,
            focusCostPerBatch = 210,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
            craftQuantity = 10,
            basicSpecLevel = 0.0,
            masteryLevel = 0.0,
        )

        val resultWithMastery = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 4, price = 100.0)),
            feePerNutrition = 0.0,
            useFocus = true,
            isPremium = false,
            availableFocus = 210.0,
            focusCostPerBatch = 210,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
            craftQuantity = 10,
            basicSpecLevel = 0.0,
            masteryLevel = 100.0,
        )

        // With 0 mastery: floor(210/210) = 1 batch with focus
        assertEquals(1, resultNoMastery.batchesWithFocus)

        // With 100 mastery: reduced cost ≈ 30, floor(210/30) = 7 batches with focus
        assertTrue(
            resultWithMastery.batchesWithFocus > resultNoMastery.batchesWithFocus,
            "Mastery should increase batchesWithFocus from ${resultNoMastery.batchesWithFocus} to more"
        )
    }

    @Test
    fun calculate_withMastery_storesReducedCostInResult() {
        val result = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 4, price = 100.0)),
            feePerNutrition = 0.0,
            useFocus = true,
            isPremium = false,
            availableFocus = null,
            focusCostPerBatch = 210,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
            craftQuantity = 1,
            basicSpecLevel = 0.0,
            masteryLevel = 100.0,
        )

        // reducedFocusCostPerBatch should be < base cost with 100 mastery
        assertTrue(
            result.reducedFocusCostPerBatch < result.focusCostPerBatch,
            "reducedFocusCostPerBatch (${result.reducedFocusCostPerBatch}) should be < base (${result.focusCostPerBatch})"
        )
    }

    @Test
    fun calculate_zeroMastery_reducedCostEqualsBaseCost() {
        val result = PotionCraftCalculator.calculate(
            ingredients = listOf(Ingredient(name = "Herb", quantity = 4, price = 100.0)),
            feePerNutrition = 0.0,
            useFocus = true,
            isPremium = false,
            availableFocus = null,
            focusCostPerBatch = 210,
            itemValue = 0,
            city = "Martlock",
            sellPrice = 600.0,
            outputQuantity = 5,
            craftQuantity = 1,
        )

        assertEquals(result.focusCostPerBatch, result.reducedFocusCostPerBatch)
    }
}
