package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.network.AlbionMarketRepository

/**
 * Fetches market prices for a set of items and applies smart price selection:
 * - If both sell and buy exist and ratio is reasonable, use sell price
 * - If ratio is too high (stale sell order), fall back to buy with markup
 * - Enforces a minimum valid price of 1 silver
 *
 * Extracted from CraftViewModel to keep the ViewModel focused on UI state.
 */
class FetchPricesUseCase(private val repository: AlbionMarketRepository) {

    companion object {
        /** If sell/buy ratio exceeds this, ignore sell price as likely stale */
        private const val SELL_BUY_RATIO_THRESHOLD = 2.0
        /** Markup applied to buy orders when no reliable sell order exists */
        private const val BUY_ORDER_MARKUP = 1.1
        /** Minimum valid price from API */
        private const val MIN_VALID_PRICE = 1
    }

    /**
     * @param itemIds All item IDs to fetch (ingredients + output potion)
     * @param city The city to query prices for
     * @param serverCode API server code ("europe", "west", "east")
     * @param potionItemId The output potion item ID (to separate from ingredient prices)
     * @return [ApiResult] wrapping [FetchedPrices] on success
     */
    suspend fun execute(
        itemIds: List<String>,
        city: String,
        serverCode: String,
        potionItemId: String,
    ): ApiResult<FetchedPrices> {
        val result = repository.getPrices(
            itemIds = itemIds,
            city = city,
            serverCode = serverCode,
        )
        return when (result) {
            is ApiResult.Success -> {
                val prices = selectPrices(
                    marketData = result.data,
                    potionItemId = potionItemId,
                )
                ApiResult.Success(prices)
            }
            is ApiResult.Error -> ApiResult.Error(result.exception)
            ApiResult.Loading -> ApiResult.Loading
        }
    }

    private fun selectPrices(
        marketData: List<com.greedmitya.albcalculator.network.MarketItemPrice>,
        potionItemId: String,
    ): FetchedPrices {
        val ingredientPrices = mutableMapOf<String, String>()
        var potionSellPrice: String? = null

        val grouped = marketData.groupBy { it.item_id }
        grouped.forEach { (id, entries) ->
            val entry = entries.firstOrNull() ?: return@forEach
            val sell = entry.sell_price_min.takeIf { it > 0 }
            val buy = entry.buy_price_max.takeIf { it > 0 }

            val selectedPrice = when {
                sell != null && buy != null ->
                    if (sell.toDouble() / buy <= SELL_BUY_RATIO_THRESHOLD) sell else null
                sell != null -> sell
                buy != null -> (buy * BUY_ORDER_MARKUP).toInt()
                else -> null
            }

            selectedPrice?.let { price ->
                val rounded = price.coerceAtLeast(MIN_VALID_PRICE)
                if (id == potionItemId) {
                    potionSellPrice = rounded.toString()
                } else {
                    ingredientPrices[id] = rounded.toString()
                }
            }
        }

        return FetchedPrices(
            ingredientPrices = ingredientPrices,
            potionSellPrice = potionSellPrice,
        )
    }
}

/**
 * Result of price fetching: separated ingredient prices and the output potion sell price.
 */
data class FetchedPrices(
    val ingredientPrices: Map<String, String>,
    val potionSellPrice: String?,
)
