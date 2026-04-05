package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.network.AlbionMarketRepository

/**
 * Fetches market prices for a set of items across all 7 cities in one API call.
 * Transforms the flat API response into rows suitable for the City Price Monitor table.
 */
class FetchMarketDataUseCase(private val repository: AlbionMarketRepository) {

    suspend fun execute(
        itemIds: List<String>,
        serverCode: String,
    ): ApiResult<List<MarketPriceRow>> {
        val result = repository.getPricesMultiCity(
            itemIds = itemIds,
            cities = ALL_CITIES,
            serverCode = serverCode,
        )
        return when (result) {
            is ApiResult.Success -> {
                val rows = buildPriceRows(
                    marketData = result.data,
                    requestedIds = itemIds,
                )
                ApiResult.Success(rows)
            }
            is ApiResult.Error -> ApiResult.Error(result.exception)
            ApiResult.Loading -> ApiResult.Loading
        }
    }

    private fun buildPriceRows(
        marketData: List<com.greedmitya.albcalculator.network.MarketItemPrice>,
        requestedIds: List<String>,
    ): List<MarketPriceRow> {
        val grouped = marketData.groupBy { it.item_id }

        return requestedIds.map { itemId ->
            val entries = grouped[itemId].orEmpty()
            val pricesByCity = ALL_CITIES.associateWith { city ->
                val entry = entries.firstOrNull { it.city == city }
                val sell = entry?.sell_price_min?.takeIf { it > 0 }
                val buy = entry?.buy_price_max?.takeIf { it > 0 }
                sell ?: buy ?: 0
            }
            val displayName = INGREDIENT_DISPLAY_NAMES[itemId] ?: itemId
            MarketPriceRow(
                itemId = itemId,
                displayName = displayName,
                pricesByCity = pricesByCity,
            )
        }
    }
}

/** A row in the City Price Monitor table: one item with prices across all cities. */
data class MarketPriceRow(
    val itemId: String,
    val displayName: String,
    val pricesByCity: Map<String, Int>,
)
