package com.greedmitya.albcalculator.model

import kotlin.math.round
import kotlin.math.pow

data class PotionCraftResult(
    val totalResources: Double,
    val withPlacementFee: Double,
    val finalCost: Double,
    val estimatedSellPrice: Double? = null
) {
    val profit: Double?
        get() = estimatedSellPrice?.let { it - finalCost }

    val profitPercent: Double?
        get() = profit?.let { if (finalCost != 0.0) (it / finalCost) * 100 else null }

    private fun Double.toFixed(decimals: Int): String {
        val factor = 10.0.pow(decimals)
        val rounded = round(this * factor) / factor
        val parts = rounded.toString().split(".")
        val integerPart = parts[0]
        val fractionalPart = if (decimals > 0) {
            (parts.getOrNull(1) ?: "").padEnd(decimals, '0').take(decimals)
        } else ""

        return if (decimals > 0) "$integerPart.$fractionalPart" else integerPart
    }

    private fun formatWithSpaces(value: Double, decimals: Int = 0): String {
        val fixed = value.toFixed(decimals)
        val parts = fixed.split(".")
        val intPart = parts[0].reversed().chunked(3).joinToString(" ").reversed()
        return if (parts.size > 1) "$intPart.${parts[1]}" else intPart
    }

    val profitSilverFormatted: String
        get() = profit?.let { "${formatWithSpaces(it)} silver" } ?: "-/-"

    val profitPercentFormatted: String
        get() = profitPercent?.let { "${formatWithSpaces(it, 1)}%" } ?: "-/-"
}

