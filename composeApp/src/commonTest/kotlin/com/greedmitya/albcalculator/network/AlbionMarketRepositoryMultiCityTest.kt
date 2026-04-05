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

class AlbionMarketRepositoryMultiCityTest {

    @Test
    fun getPricesMultiCity_correctUrlConstruction_includesCommaSeparatedCities() = runTest {
        var capturedUrl = ""
        val mockEngine = MockEngine { request ->
            capturedUrl = request.url.toString()
            respond(
                content = "[]",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        repo.getPricesMultiCity(
            itemIds = listOf("T4_BURDOCK", "T5_TEASEL"),
            cities = listOf("Caerleon", "Martlock", "Brecilien"),
            serverCode = "europe",
        )

        assertTrue(capturedUrl.contains("europe.albion-online-data.com"))
        assertTrue(capturedUrl.contains("T4_BURDOCK,T5_TEASEL"))
        assertTrue(capturedUrl.contains("locations=Caerleon,Martlock,Brecilien"))
    }

    @Test
    fun getPricesMultiCity_successfulResponse_parsesMultipleCities() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """[
                    {"item_id": "T4_BURDOCK", "city": "Caerleon", "sell_price_min": 120, "buy_price_max": 100},
                    {"item_id": "T4_BURDOCK", "city": "Martlock", "sell_price_min": 90, "buy_price_max": 70},
                    {"item_id": "T4_BURDOCK", "city": "Brecilien", "sell_price_min": 150, "buy_price_max": 130}
                ]""".trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPricesMultiCity(
            itemIds = listOf("T4_BURDOCK"),
            cities = listOf("Caerleon", "Martlock", "Brecilien"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertEquals(3, result.data.size)

        val cities = result.data.map { it.city }.toSet()
        assertTrue(cities.contains("Caerleon"))
        assertTrue(cities.contains("Martlock"))
        assertTrue(cities.contains("Brecilien"))
    }

    @Test
    fun getPricesMultiCity_serverError_returnsApiError() = runTest {
        val mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPricesMultiCity(
            itemIds = listOf("T4_BURDOCK"),
            cities = listOf("Caerleon"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Error>(result)
    }

    @Test
    fun getPricesMultiCity_singleCity_worksCorrectly() = runTest {
        var capturedUrl = ""
        val mockEngine = MockEngine { request ->
            capturedUrl = request.url.toString()
            respond(
                content = """[
                    {"item_id": "T4_BURDOCK", "city": "Caerleon", "sell_price_min": 120, "buy_price_max": 100}
                ]""".trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPricesMultiCity(
            itemIds = listOf("T4_BURDOCK"),
            cities = listOf("Caerleon"),
            serverCode = "west",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertEquals(1, result.data.size)
        assertTrue(capturedUrl.contains("locations=Caerleon"))
        assertTrue(capturedUrl.contains("west.albion-online-data.com"))
    }

    @Test
    fun getPricesMultiCity_emptyResponse_returnsEmptyList() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "[]",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPricesMultiCity(
            itemIds = listOf("T4_BURDOCK"),
            cities = listOf("Caerleon", "Martlock"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertTrue(result.data.isEmpty())
    }

    @Test
    fun getPricesMultiCity_multipleItemsMultipleCities_parsesAll() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """[
                    {"item_id": "T4_BURDOCK", "city": "Caerleon", "sell_price_min": 120, "buy_price_max": 100},
                    {"item_id": "T4_BURDOCK", "city": "Martlock", "sell_price_min": 90, "buy_price_max": 70},
                    {"item_id": "T5_TEASEL", "city": "Caerleon", "sell_price_min": 300, "buy_price_max": 250},
                    {"item_id": "T5_TEASEL", "city": "Martlock", "sell_price_min": 280, "buy_price_max": 230}
                ]""".trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val repo = AlbionMarketRepositoryImpl(HttpClient(mockEngine))

        val result = repo.getPricesMultiCity(
            itemIds = listOf("T4_BURDOCK", "T5_TEASEL"),
            cities = listOf("Caerleon", "Martlock"),
            serverCode = "europe",
        )

        assertIs<ApiResult.Success<List<MarketItemPrice>>>(result)
        assertEquals(4, result.data.size)

        val burdockEntries = result.data.filter { it.item_id == "T4_BURDOCK" }
        assertEquals(2, burdockEntries.size)

        val teaselEntries = result.data.filter { it.item_id == "T5_TEASEL" }
        assertEquals(2, teaselEntries.size)
    }
}
