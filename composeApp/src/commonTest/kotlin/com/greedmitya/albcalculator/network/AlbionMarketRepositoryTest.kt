package com.greedmitya.albcalculator.network

import com.greedmitya.albcalculator.model.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class AlbionMarketRepositoryTest {

    private fun createRepoWithMock(
        handler: MockEngine.() -> Unit = {},
        mockEngine: MockEngine,
    ): AlbionMarketRepositoryImpl {
        val client = HttpClient(mockEngine)
        return AlbionMarketRepositoryImpl(client)
    }

    @Test
    fun getPrices_successfulResponse_returnsSuccessWithParsedData() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = """[
                    {"item_id": "T4_BURDOCK", "city": "Caerleon", "sell_price_min": 150, "buy_price_max": 120},
                    {"item_id": "T4_POTION_HEAL", "city": "Caerleon", "sell_price_min": 800, "buy_price_max": 650}
                ]""".trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPrices(
            itemIds = listOf("T4_BURDOCK", "T4_POTION_HEAL"),
            city = "Caerleon",
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertEquals(2, result.data.size)
        assertEquals("T4_BURDOCK", result.data[0].item_id)
        assertEquals(150, result.data[0].sell_price_min)
        assertEquals(120, result.data[0].buy_price_max)
        assertEquals("T4_POTION_HEAL", result.data[1].item_id)
    }

    @Test
    fun getPrices_correctUrlConstruction_includesServerCityAndItems() = runTest {
        var capturedUrl = ""
        val mockEngine = MockEngine { request ->
            capturedUrl = request.url.toString()
            respond(
                content = "[]",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        repo.getPrices(
            itemIds = listOf("T4_BURDOCK", "T3_EGG"),
            city = "Brecilien",
            serverCode = "west",
        )

        assertTrue(capturedUrl.contains("west.albion-online-data.com"))
        assertTrue(capturedUrl.contains("T4_BURDOCK,T3_EGG"))
        assertTrue(capturedUrl.contains("locations=Brecilien"))
    }

    @Test
    fun getPrices_emptyResponse_returnsSuccessWithEmptyList() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "[]",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPrices(
            itemIds = listOf("T4_BURDOCK"),
            city = "Caerleon",
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertTrue(result.data.isEmpty())
    }

    @Test
    fun getPrices_zeroPricesInResponse_parsesCorrectly() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """[
                    {"item_id": "T4_BURDOCK", "city": "Caerleon", "sell_price_min": 0, "buy_price_max": 0}
                ]""".trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPrices(
            itemIds = listOf("T4_BURDOCK"),
            city = "Caerleon",
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertEquals(0, result.data[0].sell_price_min)
        assertEquals(0, result.data[0].buy_price_max)
    }

    @Test
    fun getPrices_serverError_returnsApiError() = runTest {
        val mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPrices(
            itemIds = listOf("T4_BURDOCK"),
            city = "Caerleon",
            serverCode = "europe",
        )

        assertIs<ApiResult.Error>(result)
    }

    @Test
    fun getPrices_malformedJson_returnsApiError() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "not valid json {{{",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPrices(
            itemIds = listOf("T4_BURDOCK"),
            city = "Caerleon",
            serverCode = "europe",
        )

        assertIs<ApiResult.Error>(result)
    }

    @Test
    fun getPrices_unknownFieldsInResponse_ignoresThemGracefully() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """[
                    {
                        "item_id": "T4_BURDOCK",
                        "city": "Caerleon",
                        "sell_price_min": 100,
                        "sell_price_min_date": "2026-04-01T10:00:00",
                        "buy_price_max": 80,
                        "buy_price_max_date": "2026-04-01T09:00:00",
                        "quality": 1
                    }
                ]""".trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPrices(
            itemIds = listOf("T4_BURDOCK"),
            city = "Caerleon",
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertEquals("T4_BURDOCK", result.data[0].item_id)
        assertEquals(100, result.data[0].sell_price_min)
    }

    @Test
    fun getPrices_multipleItemsMultipleEntries_parsesAll() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """[
                    {"item_id": "T6_FOXGLOVE", "city": "Caerleon", "sell_price_min": 200, "buy_price_max": 180},
                    {"item_id": "T5_EGG", "city": "Caerleon", "sell_price_min": 50, "buy_price_max": 40},
                    {"item_id": "T6_ALCOHOL", "city": "Caerleon", "sell_price_min": 300, "buy_price_max": 250},
                    {"item_id": "T6_POTION_HEAL", "city": "Caerleon", "sell_price_min": 1500, "buy_price_max": 1200}
                ]""".trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPrices(
            itemIds = listOf("T6_FOXGLOVE", "T5_EGG", "T6_ALCOHOL", "T6_POTION_HEAL"),
            city = "Caerleon",
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertEquals(4, result.data.size)
        assertEquals("T6_FOXGLOVE", result.data[0].item_id)
        assertEquals("T6_POTION_HEAL", result.data[3].item_id)
    }
}
