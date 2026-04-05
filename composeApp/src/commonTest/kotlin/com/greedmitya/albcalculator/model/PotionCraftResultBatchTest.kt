package com.greedmitya.albcalculator.model

import kotlin.test.Test
import kotlin.test.assertEquals

class PotionCraftResultBatchTest {

    private companion object {
        const val DELTA = 0.01
    }

    @Test
    fun totalProfitSilver_multipliesProfitByCraftQuantity() {
        val result = PotionCraftResult(
            totalResources = 100.0,
            withPlacementFee = 90.0,
            finalCost = 50.0,
            profitSilver = 200.0,
            craftQuantity = 10,
        )

        assertEquals(2000.0, result.totalProfitSilver, DELTA)
    }

    @Test
    fun totalCostSilver_multipliesCostByCraftQuantity() {
        val result = PotionCraftResult(
            totalResources = 100.0,
            withPlacementFee = 90.0,
            finalCost = 50.0,
            profitSilver = 200.0,
            craftQuantity = 10,
        )

        assertEquals(500.0, result.totalCostSilver, DELTA)
    }

    @Test
    fun totalProfitSilver_withQuantityOne_equalsProfitSilver() {
        val result = PotionCraftResult(
            totalResources = 100.0,
            withPlacementFee = 90.0,
            finalCost = 50.0,
            profitSilver = 200.0,
            craftQuantity = 1,
        )

        assertEquals(result.profitSilver, result.totalProfitSilver, DELTA)
    }

    @Test
    fun totalProfitFormatted_withLargeTotal_formatsWithSpaces() {
        val result = PotionCraftResult(
            totalResources = 100.0,
            withPlacementFee = 90.0,
            finalCost = 50.0,
            profitSilver = 1234.0,
            craftQuantity = 10,
        )

        // totalProfitSilver = 1234 * 10 = 12340
        assertEquals("12 340 silver", result.totalProfitFormatted)
    }

    @Test
    fun totalProfitFormatted_withNegativeProfit_formatsCorrectly() {
        val result = PotionCraftResult(
            totalResources = 100.0,
            withPlacementFee = 90.0,
            finalCost = 50.0,
            profitSilver = -100.0,
            craftQuantity = 5,
        )

        // totalProfitSilver = -100 * 5 = -500
        assertEquals("-500 silver", result.totalProfitFormatted)
    }

    @Test
    fun craftQuantity_defaultsToOne() {
        val result = PotionCraftResult(
            totalResources = 100.0,
            withPlacementFee = 90.0,
            finalCost = 50.0,
            profitSilver = 200.0,
        )

        assertEquals(1, result.craftQuantity)
    }

    @Test
    fun totalProfitSilver_withZeroProfit_returnsZero() {
        val result = PotionCraftResult(
            totalResources = 0.0,
            withPlacementFee = 0.0,
            finalCost = 0.0,
            profitSilver = 0.0,
            craftQuantity = 100,
        )

        assertEquals(0.0, result.totalProfitSilver, DELTA)
    }
}
