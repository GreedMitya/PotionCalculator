package com.greedmitya.albcalculator.network

import com.greedmitya.albcalculator.model.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

@Serializable
data class MarketItemPrice(
    val item_id: String,
    val city: String,
    val sell_price_min: Int,
    val buy_price_max: Int,
)

interface AlbionMarketRepository {
    suspend fun getPrices(
        itemIds: List<String>,
        city: String,
        serverCode: String,
    ): ApiResult<List<MarketItemPrice>>
}

class AlbionMarketRepositoryImpl(private val httpClient: HttpClient) : AlbionMarketRepository {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getPrices(
        itemIds: List<String>,
        city: String,
        serverCode: String,
    ): ApiResult<List<MarketItemPrice>> {
        val url = "https://$serverCode.albion-online-data.com/api/v2/stats/prices/${itemIds.joinToString(",")}.json?locations=$city"
        return try {
            val responseText = httpClient.get(url).body<String>()
            val priceData = json.decodeFromString<List<MarketItemPrice>>(responseText)
            ApiResult.Success(priceData)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}
