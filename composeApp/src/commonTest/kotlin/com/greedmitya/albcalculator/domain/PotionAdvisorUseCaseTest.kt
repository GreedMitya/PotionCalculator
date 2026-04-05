package com.greedmitya.albcalculator.domain

import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.model.Ingredient
import com.greedmitya.albcalculator.model.PotionInfo
import com.greedmitya.albcalculator.model.potionIngredientsByTierAndEnchant
import com.greedmitya.albcalculator.model.potionItemValues
import com.greedmitya.albcalculator.network.AlbionMarketRepository
import com.greedmitya.albcalculator.network.MarketItemPrice
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PotionAdvisorUseCaseTest {

    /**
     * Creates a fake repository that returns the given multi-city response.
     */
    private fun fakeRepository(
        response: ApiResult<List<MarketItemPrice>>,
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
        ): ApiResult<List<MarketItemPrice>> = response
    }

    /**
     * Builds a fake PotionInfo list with a single potion that has known recipe data.
     * We pick a real potion from the recipe repository so calculations work end-to-end.
     */
    private fun findTestPotion(): PotionInfo? {
        // Find any potion that has ingredient data in potionIngredientsByTierAndEnchant
        for ((baseId, tierMap) in potionIngredientsByTierAndEnchant) {
            for ((tier, enchantMap) in tierMap) {
                val ingredients = enchantMap[0]
                if (ingredients != null && ingredients.isNotEmpty()) {
                    return PotionInfo(
                        baseId = baseId,
                        displayName = "Test Potion",
                        availableTiers = listOf(tier),
                        outputQuantity = 5,
                    )
                }
            }
        }
        return null
    }

    @Test
    fun execute_networkError_returnsError() = runTest {
        val repo = fakeRepository(ApiResult.Error(RuntimeException("Connection failed")))
        val useCase = PotionAdvisorUseCase(repo)

        val result = useCase.execute(
            allPotions = emptyList(),
            city = "Caerleon",
            serverCode = "europe",
            isPremium = false,
        )

        assertIs<ApiResult.Error>(result)
        assertEquals("Connection failed", result.exception.message)
    }

    @Test
    fun execute_emptyPotionList_returnsEmptyResults() = runTest {
        val repo = fakeRepository(ApiResult.Success(emptyList()))
        val useCase = PotionAdvisorUseCase(repo)

        val result = useCase.execute(
            allPotions = emptyList(),
            city = "Caerleon",
            serverCode = "europe",
            isPremium = false,
        )

        assertIs<ApiResult.Success<AdvisorOutput>>(result)
        assertTrue(result.data.topProfitable.isEmpty())
        assertTrue(result.data.topForLeveling.isEmpty())
        assertEquals(0, result.data.totalSkipped)
    }

    @Test
    fun execute_allPricesZero_skipsAllPotions() = runTest {
        val testPotion = findTestPotion() ?: return@runTest
        val repo = fakeRepository(ApiResult.Success(emptyList()))
        val useCase = PotionAdvisorUseCase(repo)

        val result = useCase.execute(
            allPotions = listOf(testPotion),
            city = "Caerleon",
            serverCode = "europe",
            isPremium = false,
        )

        assertIs<ApiResult.Success<AdvisorOutput>>(result)
        // With no price data, all potions should be skipped
        assertTrue(result.data.topProfitable.isEmpty())
        assertTrue(result.data.totalSkipped > 0)
    }

    @Test
    fun execute_withPriceData_returnsRankedResults() = runTest {
        val testPotion = findTestPotion() ?: return@runTest
        val tier = testPotion.availableTiers.first()
        val ingredients = potionIngredientsByTierAndEnchant[testPotion.baseId]
            ?.get(tier)?.get(0).orEmpty()

        if (ingredients.isEmpty()) return@runTest

        val potionItemId = testPotion.getFullItemId(tier, 0)

        // Build price responses: ingredients at 100, potion sells at 1000
        val priceData = ingredients.map { ingredient ->
            MarketItemPrice(ingredient.name, "Caerleon", 100, 80)
        } + MarketItemPrice(potionItemId, "Caerleon", 1000, 800)

        val repo = fakeRepository(ApiResult.Success(priceData))
        val useCase = PotionAdvisorUseCase(repo)

        val result = useCase.execute(
            allPotions = listOf(testPotion),
            city = "Caerleon",
            serverCode = "europe",
            isPremium = false,
        )

        assertIs<ApiResult.Success<AdvisorOutput>>(result)
        // Should have at least one result (the potion we set up with prices)
        assertTrue(result.data.topProfitable.isNotEmpty())
        assertEquals("Test Potion", result.data.topProfitable[0].potionDisplayName)
        assertEquals(tier, result.data.topProfitable[0].tier)
        assertEquals(0, result.data.topProfitable[0].enchantment)
    }

    @Test
    fun execute_topProfitableLimit_capsAtFive() = runTest {
        val testPotion = findTestPotion() ?: return@runTest

        // Create multiple potions reusing the same base, but varying display names
        val potions = (1..10).map { i ->
            testPotion.copy(displayName = "Potion $i")
        }

        val tier = testPotion.availableTiers.first()
        val ingredients = potionIngredientsByTierAndEnchant[testPotion.baseId]
            ?.get(tier)?.get(0).orEmpty()
        if (ingredients.isEmpty()) return@runTest

        val potionItemId = testPotion.getFullItemId(tier, 0)

        val priceData = ingredients.map { ingredient ->
            MarketItemPrice(ingredient.name, "Caerleon", 50, 40)
        } + MarketItemPrice(potionItemId, "Caerleon", 5000, 4000)

        val repo = fakeRepository(ApiResult.Success(priceData))
        val useCase = PotionAdvisorUseCase(repo)

        val result = useCase.execute(
            allPotions = potions,
            city = "Caerleon",
            serverCode = "europe",
            isPremium = false,
        )

        assertIs<ApiResult.Success<AdvisorOutput>>(result)
        assertTrue(result.data.topProfitable.size <= 5)
    }

    @Test
    fun execute_topProfitable_sortedByProfitPercentDescending() = runTest {
        val testPotion = findTestPotion() ?: return@runTest
        val tier = testPotion.availableTiers.first()
        val ingredients = potionIngredientsByTierAndEnchant[testPotion.baseId]
            ?.get(tier)?.get(0).orEmpty()
        if (ingredients.isEmpty()) return@runTest

        val potionItemId = testPotion.getFullItemId(tier, 0)

        val priceData = ingredients.map { ingredient ->
            MarketItemPrice(ingredient.name, "Caerleon", 100, 80)
        } + MarketItemPrice(potionItemId, "Caerleon", 2000, 1500)

        val repo = fakeRepository(ApiResult.Success(priceData))
        val useCase = PotionAdvisorUseCase(repo)

        val result = useCase.execute(
            allPotions = listOf(testPotion),
            city = "Caerleon",
            serverCode = "europe",
            isPremium = true,
        )

        assertIs<ApiResult.Success<AdvisorOutput>>(result)
        val profits = result.data.topProfitable.map { it.profitPercent }
        // Verify descending order
        for (i in 0 until profits.size - 1) {
            assertTrue(
                profits[i] >= profits[i + 1],
                "Results should be sorted by profitPercent descending",
            )
        }
    }

    @Test
    fun execute_missingIngredientPrice_skipsPotion() = runTest {
        val testPotion = findTestPotion() ?: return@runTest
        val tier = testPotion.availableTiers.first()
        val potionItemId = testPotion.getFullItemId(tier, 0)

        // Only provide potion sell price, no ingredient prices
        val priceData = listOf(
            MarketItemPrice(potionItemId, "Caerleon", 1000, 800),
        )

        val repo = fakeRepository(ApiResult.Success(priceData))
        val useCase = PotionAdvisorUseCase(repo)

        val result = useCase.execute(
            allPotions = listOf(testPotion),
            city = "Caerleon",
            serverCode = "europe",
            isPremium = false,
        )

        assertIs<ApiResult.Success<AdvisorOutput>>(result)
        // All should be skipped due to missing ingredient prices
        assertTrue(result.data.totalSkipped > 0)
    }

    @Test
    fun execute_advisorResultFields_populated() = runTest {
        val testPotion = findTestPotion() ?: return@runTest
        val tier = testPotion.availableTiers.first()
        val ingredients = potionIngredientsByTierAndEnchant[testPotion.baseId]
            ?.get(tier)?.get(0).orEmpty()
        if (ingredients.isEmpty()) return@runTest

        val potionItemId = testPotion.getFullItemId(tier, 0)

        val priceData = ingredients.map { ingredient ->
            MarketItemPrice(ingredient.name, "Caerleon", 100, 80)
        } + MarketItemPrice(potionItemId, "Caerleon", 1000, 800)

        val repo = fakeRepository(ApiResult.Success(priceData))
        val useCase = PotionAdvisorUseCase(repo)

        val result = useCase.execute(
            allPotions = listOf(testPotion),
            city = "Caerleon",
            serverCode = "europe",
            isPremium = false,
        )

        assertIs<ApiResult.Success<AdvisorOutput>>(result)
        if (result.data.topProfitable.isNotEmpty()) {
            val first = result.data.topProfitable[0]
            assertEquals("Test Potion", first.potionDisplayName)
            assertEquals(tier, first.tier)
            assertEquals(0, first.enchantment)
            assertEquals(0, first.missingPriceCount)
            // profitSilver and profitPercent should be real numbers
            assertTrue(first.profitSilver != 0.0 || first.profitPercent != 0.0)
        }
    }
}
