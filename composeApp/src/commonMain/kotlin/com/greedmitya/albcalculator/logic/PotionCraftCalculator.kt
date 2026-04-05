package com.greedmitya.albcalculator.logic

import com.greedmitya.albcalculator.model.Ingredient
import com.greedmitya.albcalculator.model.PotionCraftResult

/**
 * Pure calculation engine for potion crafting profit.
 * MUST remain a pure function: no state, no side effects, no I/O.
 * All dependencies are passed as parameters — input → output only.
 */
object PotionCraftCalculator {

    private const val BRECILIEN_RETURN_RATE = 0.248
    private const val DEFAULT_RETURN_RATE = 0.152
    private const val CRAFTING_TAX_MULTIPLIER = 0.001125
    private const val PREMIUM_MARKET_TAX_RATE = 0.065
    private const val STANDARD_MARKET_TAX_RATE = 0.105

    private const val RARE_INGREDIENT_MARKER = "RARE"

    /** Upper bound for return rate with max focus specialization. */
    @Suppress("unused")
    private const val MAX_FOCUS_RETURN_RATE = 0.474

    fun calculate(
        ingredients: List<Ingredient>,
        feePerNutrition: Double,
        useFocus: Boolean,
        isPremium: Boolean,
        focusBasic: Double?,
        focusMastery: Double?,
        focusTotal: Double?,
        itemValue: Int,
        city: String?,
        sellPrice: Double?,
        outputQuantity: Int,
        craftQuantity: Int = 1,
    ): PotionCraftResult {
        val rareIngredients = ingredients.filter {
            it.name.contains(RARE_INGREDIENT_MARKER, ignoreCase = true)
        }
        val regularIngredients = ingredients - rareIngredients
        val rareCost = rareIngredients.sumOf { (it.price ?: 0.0) * it.quantity }
        val regularRawCost = regularIngredients.sumOf { (it.price ?: 0.0) * it.quantity }

        val returnRate = resolveReturnRate(
            city = city,
            useFocus = useFocus,
            focusBasic = focusBasic,
            focusMastery = focusMastery,
            focusTotal = focusTotal,
        )

        val regularAfterReturn = regularRawCost * (1 - returnRate)
        val totalCostAfterReturn = rareCost + regularAfterReturn
        val craftingTax = feePerNutrition * itemValue * CRAFTING_TAX_MULTIPLIER
        val craftingTaxPerItem = craftingTax / outputQuantity
        val costPerItem = (totalCostAfterReturn / outputQuantity) + craftingTaxPerItem
        val taxRate = if (isPremium) PREMIUM_MARKET_TAX_RATE else STANDARD_MARKET_TAX_RATE
        val netSell = (sellPrice ?: 0.0) * (1 - taxRate)
        val profitSilver = netSell - costPerItem

        return PotionCraftResult(
            totalResources = rareCost + regularRawCost,
            withPlacementFee = totalCostAfterReturn,
            finalCost = costPerItem,
            estimatedSellPrice = sellPrice,
            profitSilver = profitSilver,
            craftQuantity = craftQuantity,
        )
    }

    /**
     * Resolves the resource return rate based on city and focus state.
     *
     * TODO: Implement actual Albion Online focus crafting formula when confirmed.
     * focusBasic/focusMastery/focusTotal are the 3 specialization fields from Craft+ UI.
     * At max specialization (100) + Brecilien, max return is ~47.4%.
     * Currently returns base rate unchanged until formula is researched.
     */
    private fun resolveReturnRate(
        city: String?,
        useFocus: Boolean,
        focusBasic: Double?,
        focusMastery: Double?,
        focusTotal: Double?,
    ): Double {
        val baseRate = when (city) {
            "Brecilien" -> BRECILIEN_RETURN_RATE
            else -> DEFAULT_RETURN_RATE
        }
        if (!useFocus) return baseRate
        // Placeholder: returns base rate until focus formula is implemented.
        return baseRate
    }
}

