package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.network.AlbionMarketRepository
import com.greedmitya.albcalculator.network.MarketItemPrice
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class FetchPricesUseCaseTest {

    private fun fakeRepository(
        response: ApiResult<List<MarketItemPrice>>,
    ): AlbionMarketRepository = object : AlbionMarketRepository {
        override suspend fun getPrices(
            itemIds: List<String>,
            city: String,
            serverCode: String,
        ): ApiResult<List<MarketItemPrice>> = response

        override suspend fun getPricesMultiCity(
            itemIds: List<String>,
            cities: List<String>,
            serverCode: String,
        ): ApiResult<List<MarketItemPrice>> = response
    }

    @Test
    fun execute_withBothSellAndBuy_usesSelWhenRatioReasonable() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice(
                        item_id = "T4_HERB",
                        city = "Caerleon",
                        sell_price_min = 100,
                        buy_price_max = 80,
                    ),
                )
            )
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_HERB", "T4_POTION"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T4_POTION",
        )

        assertIs<ApiResult.Success<FetchedPrices>>(result)
        assertEquals("100", result.data.ingredientPrices["T4_HERB"])
    }

    @Test
    fun execute_withHighSellBuyRatio_ignoresSellPrice() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice(
                        item_id = "T4_HERB",
                        city = "Caerleon",
                        sell_price_min = 500,
                        buy_price_max = 100,
                    ),
                )
            )
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_HERB", "T4_POTION"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T4_POTION",
        )

        assertIs<ApiResult.Success<FetchedPrices>>(result)
        // Ratio 500/100 = 5.0 > 2.0 threshold, so sell is ignored, price is null for this item
        assertNull(result.data.ingredientPrices["T4_HERB"])
    }

    @Test
    fun execute_withOnlySellPrice_usesSellPrice() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice(
                        item_id = "T4_HERB",
                        city = "Caerleon",
                        sell_price_min = 150,
                        buy_price_max = 0,
                    ),
                )
            )
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_HERB", "T4_POTION"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T4_POTION",
        )

        assertIs<ApiResult.Success<FetchedPrices>>(result)
        assertEquals("150", result.data.ingredientPrices["T4_HERB"])
    }

    @Test
    fun execute_withOnlyBuyPrice_appliesMarkup() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice(
                        item_id = "T4_HERB",
                        city = "Caerleon",
                        sell_price_min = 0,
                        buy_price_max = 100,
                    ),
                )
            )
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_HERB", "T4_POTION"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T4_POTION",
        )

        assertIs<ApiResult.Success<FetchedPrices>>(result)
        // 100 * 1.1 = 110
        assertEquals("110", result.data.ingredientPrices["T4_HERB"])
    }

    @Test
    fun execute_withNoPrices_skipsItem() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice(
                        item_id = "T4_HERB",
                        city = "Caerleon",
                        sell_price_min = 0,
                        buy_price_max = 0,
                    ),
                )
            )
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_HERB", "T4_POTION"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T4_POTION",
        )

        assertIs<ApiResult.Success<FetchedPrices>>(result)
        assertNull(result.data.ingredientPrices["T4_HERB"])
    }

    @Test
    fun execute_separatesPotionPriceFromIngredients() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice(
                        item_id = "T4_HERB",
                        city = "Caerleon",
                        sell_price_min = 100,
                        buy_price_max = 80,
                    ),
                    MarketItemPrice(
                        item_id = "T4_POTION_HEAL",
                        city = "Caerleon",
                        sell_price_min = 500,
                        buy_price_max = 400,
                    ),
                )
            )
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_HERB", "T4_POTION_HEAL"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T4_POTION_HEAL",
        )

        assertIs<ApiResult.Success<FetchedPrices>>(result)
        assertEquals("100", result.data.ingredientPrices["T4_HERB"])
        assertNull(result.data.ingredientPrices["T4_POTION_HEAL"])
        assertEquals("500", result.data.potionSellPrice)
    }

    @Test
    fun execute_enforcesMinimumPrice() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice(
                        item_id = "T4_HERB",
                        city = "Caerleon",
                        sell_price_min = 0,
                        buy_price_max = 0,
                    ),
                    MarketItemPrice(
                        item_id = "T4_CHEAP",
                        city = "Caerleon",
                        sell_price_min = 1,
                        buy_price_max = 0,
                    ),
                )
            )
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_HERB", "T4_CHEAP", "T4_POTION"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T4_POTION",
        )

        assertIs<ApiResult.Success<FetchedPrices>>(result)
        assertEquals("1", result.data.ingredientPrices["T4_CHEAP"])
    }

    @Test
    fun execute_networkError_returnsError() = runTest {
        val repo = fakeRepository(
            ApiResult.Error(RuntimeException("Connection timeout"))
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_HERB"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T4_POTION",
        )

        assertIs<ApiResult.Error>(result)
        assertEquals("Connection timeout", result.exception.message)
    }

    @Test
    fun execute_multipleIngredients_allProcessedCorrectly() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice(
                        item_id = "T6_FOXGLOVE",
                        city = "Caerleon",
                        sell_price_min = 200,
                        buy_price_max = 180,
                    ),
                    MarketItemPrice(
                        item_id = "T5_EGG",
                        city = "Caerleon",
                        sell_price_min = 50,
                        buy_price_max = 40,
                    ),
                    MarketItemPrice(
                        item_id = "T6_ALCOHOL",
                        city = "Caerleon",
                        sell_price_min = 300,
                        buy_price_max = 250,
                    ),
                    MarketItemPrice(
                        item_id = "T6_POTION_HEAL",
                        city = "Caerleon",
                        sell_price_min = 1500,
                        buy_price_max = 1200,
                    ),
                )
            )
        )
        val useCase = FetchPricesUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T6_FOXGLOVE", "T5_EGG", "T6_ALCOHOL", "T6_POTION_HEAL"),
            city = "Caerleon",
            serverCode = "europe",
            potionItemId = "T6_POTION_HEAL",
        )

        assertIs<ApiResult.Success<FetchedPrices>>(result)
        assertEquals(3, result.data.ingredientPrices.size)
        assertEquals("200", result.data.ingredientPrices["T6_FOXGLOVE"])
        assertEquals("50", result.data.ingredientPrices["T5_EGG"])
        assertEquals("300", result.data.ingredientPrices["T6_ALCOHOL"])
        assertEquals("1500", result.data.potionSellPrice)
    }
}
