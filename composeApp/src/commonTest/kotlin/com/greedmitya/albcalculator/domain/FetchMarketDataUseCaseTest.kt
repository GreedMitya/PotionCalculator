package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.network.AlbionMarketRepository
import com.greedmitya.albcalculator.network.MarketItemPrice
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class FetchMarketDataUseCaseTest {

    private fun fakeRepository(
        multiCityResponse: ApiResult<List<MarketItemPrice>>,
    ): AlbionMarketRepository = object : AlbionMarketRepository {
        override suspend fun getPrices(
            itemIds: List<String>,
            city: String,
            serverCode: String,
        ): ApiResult<List<MarketItemPrice>> = throw UnsupportedOperationException()

        override suspend fun getPricesMultiCity(
            itemIds: List<String>,
            cities: List<String>,
            serverCode: String,
        ): ApiResult<List<MarketItemPrice>> = multiCityResponse
    }

    @Test
    fun execute_successfulResponse_returnsRowsForEachRequestedItem() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice("T4_BURDOCK", "Caerleon", 120, 100),
                    MarketItemPrice("T4_BURDOCK", "Martlock", 90, 70),
                    MarketItemPrice("T5_TEASEL", "Caerleon", 300, 250),
                ),
            ),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK", "T5_TEASEL"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        assertEquals(2, result.data.size)
        assertEquals("T4_BURDOCK", result.data[0].itemId)
        assertEquals("T5_TEASEL", result.data[1].itemId)
    }

    @Test
    fun execute_buildsPriceByCityMap_prefersSellOverBuy() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice("T4_BURDOCK", "Caerleon", 120, 100),
                    MarketItemPrice("T4_BURDOCK", "Martlock", 0, 80),
                ),
            ),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        val row = result.data[0]
        // Caerleon has sell_price_min = 120, should use sell
        assertEquals(120, row.pricesByCity["Caerleon"])
        // Martlock has sell = 0, should fall back to buy = 80
        assertEquals(80, row.pricesByCity["Martlock"])
    }

    @Test
    fun execute_missingCityData_returnsZeroForThatCity() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice("T4_BURDOCK", "Caerleon", 120, 100),
                ),
            ),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        val row = result.data[0]
        assertEquals(120, row.pricesByCity["Caerleon"])
        // Cities with no data should be 0
        assertEquals(0, row.pricesByCity["Bridgewatch"])
        assertEquals(0, row.pricesByCity["Brecilien"])
    }

    @Test
    fun execute_allCitiesPresent_rowContainsAllSeven() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                ALL_CITIES.map { city ->
                    MarketItemPrice("T4_BURDOCK", city, 100, 80)
                },
            ),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        val row = result.data[0]
        assertEquals(ALL_CITIES.size, row.pricesByCity.size)
        ALL_CITIES.forEach { city ->
            assertEquals(100, row.pricesByCity[city])
        }
    }

    @Test
    fun execute_usesDisplayNameFromMap() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice("T4_BURDOCK", "Caerleon", 100, 80),
                ),
            ),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        assertEquals("T4 Burdock", result.data[0].displayName)
    }

    @Test
    fun execute_unknownItemId_usesItemIdAsDisplayName() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice("T99_UNKNOWN", "Caerleon", 50, 30),
                ),
            ),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T99_UNKNOWN"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        assertEquals("T99_UNKNOWN", result.data[0].displayName)
    }

    @Test
    fun execute_preservesRequestedItemOrder() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    // API returns in different order than requested
                    MarketItemPrice("T5_TEASEL", "Caerleon", 300, 250),
                    MarketItemPrice("T4_BURDOCK", "Caerleon", 120, 100),
                ),
            ),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK", "T5_TEASEL"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        assertEquals("T4_BURDOCK", result.data[0].itemId)
        assertEquals("T5_TEASEL", result.data[1].itemId)
    }

    @Test
    fun execute_emptyResponse_returnsRowsWithZeroPrices() = runTest {
        val repo = fakeRepository(ApiResult.Success(emptyList()))
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        assertEquals(1, result.data.size)
        assertTrue(result.data[0].pricesByCity.values.all { it == 0 })
    }

    @Test
    fun execute_networkError_returnsError() = runTest {
        val repo = fakeRepository(
            ApiResult.Error(RuntimeException("Network timeout")),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Error>(result)
        assertEquals("Network timeout", result.exception.message)
    }

    @Test
    fun execute_zeroPrices_returnsZeroInMap() = runTest {
        val repo = fakeRepository(
            ApiResult.Success(
                listOf(
                    MarketItemPrice("T4_BURDOCK", "Caerleon", 0, 0),
                ),
            ),
        )
        val useCase = FetchMarketDataUseCase(repo)

        val result = useCase.execute(
            itemIds = listOf("T4_BURDOCK"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketPriceRow>>>(result)
        assertEquals(0, result.data[0].pricesByCity["Caerleon"])
    }
}
