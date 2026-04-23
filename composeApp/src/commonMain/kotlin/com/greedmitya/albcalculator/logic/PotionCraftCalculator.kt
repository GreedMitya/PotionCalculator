package com.greedmitya.albcalculator.logic

import com.greedmitya.albcalculator.model.Ingredient
import com.greedmitya.albcalculator.model.PotionCraftResult
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Pure calculation engine for potion crafting profit.
 * MUST remain a pure function: no state, no side effects, no I/O.
 * All dependencies are passed as parameters — input → output only.
 */
object PotionCraftCalculator {

    // Raw crafting bonus per location (from craftingmodifiers.xml in ao-bin-dumps).
    // returnRate = bonus / (1 + bonus).
    private const val BRECILIEN_CITY_BONUS = 0.33   // 18% base + 15% potion specialty
    private const val DEFAULT_CITY_BONUS = 0.18     // 18% base for all other cities

    // Focus adds this to the city bonus when crafting with focus.
    // Derived by back-solving from the known Brecilien+focus maximum:
    //   (0.33 + FOCUS_BONUS) / (1 + 0.33 + FOCUS_BONUS) = 0.474
    //   → FOCUS_BONUS ≈ 0.571
    private const val FOCUS_CRAFTING_BONUS = 0.571

    // Precomputed return rates exposed for tests (derived from the formula, not hardcoded).
    internal val BRECILIEN_RETURN_RATE = BRECILIEN_CITY_BONUS / (1 + BRECILIEN_CITY_BONUS)
    internal val DEFAULT_RETURN_RATE = DEFAULT_CITY_BONUS / (1 + DEFAULT_CITY_BONUS)
    internal val BRECILIEN_FOCUS_RETURN_RATE =
        (BRECILIEN_CITY_BONUS + FOCUS_CRAFTING_BONUS) / (1 + BRECILIEN_CITY_BONUS + FOCUS_CRAFTING_BONUS)
    internal val DEFAULT_FOCUS_RETURN_RATE =
        (DEFAULT_CITY_BONUS + FOCUS_CRAFTING_BONUS) / (1 + DEFAULT_CITY_BONUS + FOCUS_CRAFTING_BONUS)

    // Each point of efficiency halves the focus cost at 10,000 pts (community-verified formula).
    private const val FOCUS_REDUCTION_HALF_LIFE = 10_000.0

    // Efficiency points contributed by each specialization tier (Destiny Board hierarchy).
    private const val GENERAL_SPEC_EFFICIENCY_PTS = 30.0   // Top-level crafting node, max 100 levels
    private const val CATEGORY_SPEC_EFFICIENCY_PTS = 18.0  // Alchemist category node, max ~1,500 levels
    private const val MASTERY_EFFICIENCY_PTS = 250.0        // Item-specific mastery node

    private const val CRAFTING_TAX_MULTIPLIER = 0.001125
    private const val PREMIUM_MARKET_TAX_RATE = 0.065
    private const val STANDARD_MARKET_TAX_RATE = 0.105

    private const val RARE_INGREDIENT_MARKER = "RARE"

    // reducedCost = baseCost × 0.5^(totalPts / 10,000)
    internal fun reduceFocusCost(
        baseCost: Int,
        generalSpecLevel: Double = 0.0,
        basicSpecLevel: Double = 0.0,
        masteryLevel: Double = 0.0,
    ): Int {
        if (baseCost <= 0) return 0
        val efficiencyPts = generalSpecLevel * GENERAL_SPEC_EFFICIENCY_PTS +
                basicSpecLevel * CATEGORY_SPEC_EFFICIENCY_PTS +
                masteryLevel * MASTERY_EFFICIENCY_PTS
        return (baseCost * 0.5.pow(efficiencyPts / FOCUS_REDUCTION_HALF_LIFE)).roundToInt()
    }

    fun calculate(
        ingredients: List<Ingredient>,
        feePerNutrition: Double,
        useFocus: Boolean,
        isPremium: Boolean,
        availableFocus: Double?,
        focusCostPerBatch: Int,
        itemValue: Int,
        city: String?,
        sellPrice: Double?,
        outputQuantity: Int,
        craftQuantity: Int = 1,
        generalSpecLevel: Double = 0.0,
        basicSpecLevel: Double = 0.0,
        masteryLevel: Double = 0.0,
    ): PotionCraftResult {
        val rareIngredients = ingredients.filter {
            it.name.contains(RARE_INGREDIENT_MARKER, ignoreCase = true)
        }
        val regularIngredients = ingredients - rareIngredients
        val rareCost = rareIngredients.sumOf { (it.price ?: 0.0) * it.quantity }
        val regularRawCost = regularIngredients.sumOf { (it.price ?: 0.0) * it.quantity }

        val effectiveFocusCost = reduceFocusCost(focusCostPerBatch, generalSpecLevel, basicSpecLevel, masteryLevel)

        val (returnRate, batchesWithFocus) = resolveReturnRate(
            city = city,
            useFocus = useFocus,
            availableFocus = availableFocus,
            focusCostPerBatch = effectiveFocusCost,
            craftQuantity = craftQuantity,
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
            outputQuantity = outputQuantity,
            batchesWithFocus = batchesWithFocus,
            focusCostPerBatch = focusCostPerBatch,
            reducedFocusCostPerBatch = effectiveFocusCost,
            effectiveReturnRate = returnRate,
        )
    }

    /**
     * Returns (effectiveReturnRate, batchesWithFocus).
     *
     * Without focus: pure city bonus rate.
     * With focus + enough available focus: full focus rate for all batches.
     * With focus + partial focus: blended average of focus-rate and base-rate batches,
     *   so profit reflects the realistic average across the planned session.
     */
    private fun resolveReturnRate(
        city: String?,
        useFocus: Boolean,
        availableFocus: Double?,
        focusCostPerBatch: Int,
        craftQuantity: Int,
    ): Pair<Double, Int> {
        val cityBonus = if (city == "Brecilien") BRECILIEN_CITY_BONUS else DEFAULT_CITY_BONUS
        val baseRate = cityBonus / (1 + cityBonus)

        if (!useFocus) return baseRate to 0

        val focusRate = (cityBonus + FOCUS_CRAFTING_BONUS) / (1 + cityBonus + FOCUS_CRAFTING_BONUS)

        val batchesWithFocus = when {
            availableFocus == null || focusCostPerBatch <= 0 -> craftQuantity
            else -> minOf((availableFocus / focusCostPerBatch).toInt(), craftQuantity)
        }
        val batchesWithout = craftQuantity - batchesWithFocus

        val blended = if (craftQuantity > 0)
            (batchesWithFocus * focusRate + batchesWithout * baseRate) / craftQuantity
        else
            focusRate

        return blended to batchesWithFocus
    }
}
