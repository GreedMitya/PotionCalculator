package com.greedmitya.albcalculator.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PotionCraftResultTest {

    private companion object {
        const val DELTA = 0.01
    }

    @Test
    fun profitPercent_withPositiveCost_calculatesCorrectly() {
        val result = PotionCraftResult(
            totalResources = 100.0,
            withPlacementFee = 90.0,
            finalCost = 200.0,
            estimatedSellPrice = 300.0,
            profitSilver = 100.0,
        )

        // profitPercent = (100 / 200) * 100 = 50%
        assertEquals(50.0, result.profitPercent ?: 0.0, DELTA)
    }

    @Test
    fun profitPercent_withZeroCost_returnsNull() {
        val result = PotionCraftResult(
            totalResources = 0.0,
            withPlacementFee = 0.0,
            finalCost = 0.0,
            profitSilver = 100.0,
        )

        assertNull(result.profitPercent)
    }

    @Test
    fun profitPercent_withNegativeProfit_returnsNegativePercent() {
        val result = PotionCraftResult(
            totalResources = 100.0,
            withPlacementFee = 90.0,
            finalCost = 200.0,
            profitSilver = -50.0,
        )

        // profitPercent = (-50 / 200) * 100 = -25%
        assertEquals(-25.0, result.profitPercent ?: 0.0, DELTA)
    }

    @Test
    fun profitSilverFormatted_withLargeNumber_formatsWithSpaces() {
        val result = PotionCraftResult(
            totalResources = 0.0,
            withPlacementFee = 0.0,
            finalCost = 0.0,
            profitSilver = 12345.0,
        )

        assertEquals("12 345 silver", result.profitSilverFormatted)
    }

    @Test
    fun profitSilverFormatted_withSmallNumber_formatsCorrectly() {
        val result = PotionCraftResult(
            totalResources = 0.0,
            withPlacementFee = 0.0,
            finalCost = 0.0,
            profitSilver = 42.0,
        )

        assertEquals("42 silver", result.profitSilverFormatted)
    }

    @Test
    fun profitPercentFormatted_withZeroCost_showsPlaceholder() {
        val result = PotionCraftResult(
            totalResources = 0.0,
            withPlacementFee = 0.0,
            finalCost = 0.0,
            profitSilver = 0.0,
        )

        assertEquals("-/-", result.profitPercentFormatted)
    }

    @Test
    fun profitPercentFormatted_withValidPercent_showsOneDecimal() {
        val result = PotionCraftResult(
            totalResources = 0.0,
            withPlacementFee = 0.0,
            finalCost = 100.0,
            profitSilver = 33.33,
        )

        // 33.33 / 100 * 100 = 33.33 → formatted as "33.3%"
        assertEquals("33.3%", result.profitPercentFormatted)
    }
}
