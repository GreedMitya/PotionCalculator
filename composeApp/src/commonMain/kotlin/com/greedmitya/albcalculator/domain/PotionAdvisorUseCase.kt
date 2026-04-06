package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.logic.PotionCraftCalculator
import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.model.Ingredient
import com.greedmitya.albcalculator.model.PotionInfo
import com.greedmitya.albcalculator.model.potionIngredientsByTierAndEnchant
import com.greedmitya.albcalculator.model.potionItemValues
import com.greedmitya.albcalculator.network.AlbionMarketRepository

/**
 * Analyzes all potion/tier/enchantment combinations for a given city and server,
 * ranking them by profitability.
 *
 * Returns top 5 profitable and top 3 "best for leveling" (small loss, high item value = XP).
 */
class PotionAdvisorUseCase(private val repository: AlbionMarketRepository) {

    companion object {
        private const val TOP_PROFITABLE_COUNT = 5
        private const val TOP_LEVELING_COUNT = 3
        private const val MAX_LEVELING_LOSS_PERCENT = -5.0
        private const val DEFAULT_FEE_PER_NUTRITION = 100.0
    }

    suspend fun execute(
        allPotions: List<PotionInfo>,
        city: String,
        serverCode: String,
        isPremium: Boolean,
    ): ApiResult<AdvisorOutput> {
        val allItemIds = collectAllItemIds(allPotions)

        val apiResult = repository.getPricesMultiCity(
            itemIds = allItemIds,
            cities = listOf(city),
            serverCode = serverCode,
        )

        return when (apiResult) {
            is ApiResult.Success -> {
                val priceMap = apiResult.data.associate { it.item_id to selectPrice(it) }
                val output = analyze(
                    allPotions = allPotions,
                    priceMap = priceMap,
                    city = city,
                    isPremium = isPremium,
                )
                ApiResult.Success(output)
            }
            is ApiResult.Error -> ApiResult.Error(apiResult.exception)
            ApiResult.Loading -> ApiResult.Loading
        }
    }

    private fun collectAllItemIds(allPotions: List<PotionInfo>): List<String> {
        val ingredientIds = mutableSetOf<String>()
        val potionIds = mutableSetOf<String>()

        allPotions.forEach { potion ->
            val enchantRange = if (potion.hasEnchants) 0..3 else 0..0
            potion.availableTiers.forEach { tier ->
                for (enchant in enchantRange) {
                    potionIds.add(potion.getFullItemId(tier, enchant))
                    val ingredients = potionIngredientsByTierAndEnchant[potion.baseId]
                        ?.get(tier)?.get(enchant).orEmpty()
                    ingredients.forEach { ingredientIds.add(it.name) }
                }
            }
        }

        return (ingredientIds + potionIds).toList()
    }

    private fun selectPrice(item: com.greedmitya.albcalculator.network.MarketItemPrice): Int {
        val sell = item.sell_price_min.takeIf { it > 0 }
        val buy = item.buy_price_max.takeIf { it > 0 }
        return sell ?: buy ?: 0
    }

    private fun analyze(
        allPotions: List<PotionInfo>,
        priceMap: Map<String, Int>,
        city: String,
        isPremium: Boolean,
    ): AdvisorOutput {
        val results = mutableListOf<PotionAdvisorResult>()
        var totalSkipped = 0

        allPotions.forEach { potion ->
            val enchantRange = if (potion.hasEnchants) 0..3 else 0..0
            potion.availableTiers.forEach { tier ->
                for (enchant in enchantRange) {
                    val ingredients = potionIngredientsByTierAndEnchant[potion.baseId]
                        ?.get(tier)?.get(enchant).orEmpty()

                    if (ingredients.isEmpty()) continue

                    val potionItemId = potion.getFullItemId(tier, enchant)
                    val sellPrice = priceMap[potionItemId] ?: 0
                    val missingCount = ingredients.count { (priceMap[it.name] ?: 0) == 0 }

                    if (sellPrice == 0 || missingCount > 0) {
                        totalSkipped++
                        continue
                    }

                    val pricedIngredients = ingredients.map {
                        it.copy(price = (priceMap[it.name] ?: 0).toDouble())
                    }

                    val itemValue = potionItemValues[potion.baseId]?.get(tier) ?: 0

                    val calcResult = PotionCraftCalculator.calculate(
                        ingredients = pricedIngredients,
                        feePerNutrition = DEFAULT_FEE_PER_NUTRITION,
                        useFocus = false,
                        isPremium = isPremium,
                        focusBasic = null,
                        focusMastery = null,
                        focusTotal = null,
                        itemValue = itemValue,
                        city = city,
                        sellPrice = sellPrice.toDouble(),
                        outputQuantity = potion.outputQuantity,
                    )

                    val profitPercent = calcResult.profitPercent ?: 0.0

                    results.add(
                        PotionAdvisorResult(
                            potionDisplayName = potion.displayName,
                            tier = tier,
                            enchantment = enchant,
                            profitSilver = calcResult.profitSilver,
                            profitPercent = profitPercent,
                            missingPriceCount = 0,
                        )
                    )
                }
            }
        }

        // Only crafts with a positive margin — loss crafts belong in leveling, not here
        val sortedProfitable = results
            .filter { it.profitPercent > 0 }
            .sortedByDescending { it.profitPercent }

        val topProfitable = sortedProfitable.take(TOP_PROFITABLE_COUNT)

        // Full list — no cap; UI controls how many are shown via "Show more"
        val allProfitable = sortedProfitable

        val sortedLeveling = results
            .filter { it.profitPercent in MAX_LEVELING_LOSS_PERCENT..0.0 }
            .sortedByDescending { it.profitPercent }

        val topForLeveling = sortedLeveling.take(TOP_LEVELING_COUNT)

        // Full leveling list — UI controls expansion
        val allForLeveling = sortedLeveling

        return AdvisorOutput(
            topProfitable = topProfitable,
            topForLeveling = topForLeveling,
            totalSkipped = totalSkipped,
            allProfitable = allProfitable,
            allForLeveling = allForLeveling,
        )
    }
}
