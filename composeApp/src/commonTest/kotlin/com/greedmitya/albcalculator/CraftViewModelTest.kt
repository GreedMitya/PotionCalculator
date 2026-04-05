package com.greedmitya.albcalculator

import com.greedmitya.albcalculator.domain.CalculateProfitUseCase
import com.greedmitya.albcalculator.domain.FavoritesRepository
import com.greedmitya.albcalculator.domain.FetchPricesUseCase
import com.greedmitya.albcalculator.domain.FetchedPrices
import com.greedmitya.albcalculator.domain.InMemoryAppPremiumRepository
import com.greedmitya.albcalculator.domain.InMemoryFavoritesRepository
import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.model.FavoriteRecipe
import com.greedmitya.albcalculator.network.AlbionMarketRepository
import com.greedmitya.albcalculator.network.MarketItemPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CraftViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeRepository: FakeAlbionMarketRepository
    private lateinit var fakeFavoritesRepo: InMemoryFavoritesRepository
    private lateinit var viewModel: CraftViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeAlbionMarketRepository()
        fakeFavoritesRepo = InMemoryFavoritesRepository()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(
        favoritesRepo: FavoritesRepository = fakeFavoritesRepo,
    ): CraftViewModel {
        val fetchPricesUseCase = FetchPricesUseCase(fakeRepository)
        val calculateProfitUseCase = CalculateProfitUseCase()
        return CraftViewModel(
            fetchPricesUseCase = fetchPricesUseCase,
            calculateProfitUseCase = calculateProfitUseCase,
            favoritesRepository = favoritesRepo,
            appPremiumRepository = InMemoryAppPremiumRepository(),
        ).also { viewModel = it }
    }

    // region Initialization

    @Test
    fun init_loadsServerFromRepository() = runTest {
        fakeFavoritesRepo.saveSelectedServer("North America")
        val vm = createViewModel()
        advanceUntilIdle()
        assertEquals("North America", vm.selectedServer)
    }

    @Test
    fun init_loadsFavoritesFromRepository() = runTest {
        val saved = listOf(
            FavoriteRecipe(
                potionName = "Healing Potion",
                tier = "T4",
                enchantment = 0,
                city = "Caerleon",
            ),
        )
        fakeFavoritesRepo.saveFavorites(saved)
        val vm = createViewModel()
        advanceUntilIdle()
        assertEquals(1, vm.favorites.size)
        assertEquals("Healing Potion", vm.favorites[0].potionName)
    }

    // endregion

    // region Potion Selection

    @Test
    fun onPotionSelected_setsDefaultTierAndEnchantment() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()

        assertEquals("Healing Potion", vm.selectedPotion)
        assertEquals("T2", vm.selectedTier)
        assertEquals("Normal (.0)", vm.selectedEnchantment)
    }

    @Test
    fun availableTiers_returnsCorrectTiersForPotion() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        assertEquals(listOf("T2", "T4", "T6"), vm.availableTiers)
    }

    @Test
    fun outputQuantity_returnsCorrectDefault() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        // No potion selected → default 5
        assertEquals(5, vm.outputQuantity)

        // Alcohol has outputQuantity = 1
        vm.onPotionSelected("Alcohol")
        assertEquals(1, vm.outputQuantity)

        // Acid Potion has outputQuantity = 10
        vm.onPotionSelected("Acid Potion")
        assertEquals(10, vm.outputQuantity)
    }

    @Test
    fun getFullItemId_withEnchantZero_returnsCorrectId() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedTier = "T4"
        vm.selectedEnchantment = "Normal (.0)"

        assertEquals("T4_POTION_HEAL", vm.getFullItemId())
    }

    @Test
    fun getFullItemId_withEnchantNonZero_returnsIdWithAtSign() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedTier = "T6"
        vm.selectedEnchantment = "Outstanding (.2)"

        assertEquals("T6_POTION_HEAL@2", vm.getFullItemId())
    }

    @Test
    fun getFullItemId_withNoSelection_returnsNull() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        assertNull(vm.getFullItemId())
    }

    // endregion

    // region Validation

    @Test
    fun isReadyToCalculate_allFieldsFilled_returnsTrue() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"
        vm.feePerNutritionInput = "100"
        vm.potionSellPrice = "500"

        // Fill ingredient prices for T2 Healing Potion (Normal enchant = just T2_AGARIC)
        val recipe = vm.getRecipeForSelected()
        recipe.forEach { vm.ingredientPrices[it.name] = "50" }

        assertTrue(vm.isReadyToCalculate)
    }

    @Test
    fun isReadyToCalculate_missingCity_returnsFalse() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.feePerNutritionInput = "100"
        vm.potionSellPrice = "500"

        assertFalse(vm.isReadyToCalculate)
    }

    @Test
    fun isReadyToCalculate_invalidFee_returnsFalse() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"
        vm.feePerNutritionInput = "not_a_number"
        vm.potionSellPrice = "500"

        val recipe = vm.getRecipeForSelected()
        recipe.forEach { vm.ingredientPrices[it.name] = "50" }

        assertFalse(vm.isReadyToCalculate)
    }

    @Test
    fun isReadyForMarket_basicSelectionFilled_returnsTrue() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"

        assertTrue(vm.isReadyForMarket)
    }

    @Test
    fun validationErrors_calculateMode_flagsMissingFields() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        // Nothing selected, trigger calculate validation
        vm.triggerValidationForCalculate()
        advanceUntilIdle()

        assertTrue(vm.isPotionError)
        assertTrue(vm.isTierError)
        assertTrue(vm.isEnchantmentError)
        assertTrue(vm.isCityError)
        assertTrue(vm.isFeeError)
        assertTrue(vm.isSellPriceError)
    }

    @Test
    fun validationErrors_marketMode_onlyFlagsSelectionFields() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.triggerValidationForMarket()
        advanceUntilIdle()

        assertTrue(vm.isPotionError)
        assertTrue(vm.isCityError)
        // Market mode does NOT require fee/sell price
        assertFalse(vm.isFeeError)
        assertFalse(vm.isSellPriceError)
    }

    @Test
    fun validationErrors_afterFillingFields_clearErrors() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"
        vm.feePerNutritionInput = "100"
        vm.potionSellPrice = "500"

        vm.triggerValidationForCalculate()
        advanceUntilIdle()

        assertFalse(vm.isPotionError)
        assertFalse(vm.isTierError)
        assertFalse(vm.isCityError)
        assertFalse(vm.isFeeError)
        assertFalse(vm.isSellPriceError)
    }

    // endregion

    // region Profit Calculation

    @Test
    fun calculateProfit_withValidInputs_producesResult() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"
        vm.feePerNutritionInput = "100"
        vm.potionSellPrice = "500"
        vm.isPremium = false

        val recipe = vm.getRecipeForSelected()
        recipe.forEach { vm.ingredientPrices[it.name] = "50" }

        vm.calculateProfit()

        val result = vm.result
        assertNotNull(result)
        assertTrue(result.profitSilver != 0.0)
    }

    @Test
    fun calculateProfit_premiumVsStandard_differsByTaxRate() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"
        vm.feePerNutritionInput = "100"
        vm.potionSellPrice = "1000"

        val recipe = vm.getRecipeForSelected()
        recipe.forEach { vm.ingredientPrices[it.name] = "50" }

        // Standard
        vm.isPremium = false
        vm.calculateProfit()
        val standardProfit = vm.result?.profitSilver ?: 0.0

        // Premium
        vm.isPremium = true
        vm.calculateProfit()
        val premiumProfit = vm.result?.profitSilver ?: 0.0

        assertTrue(
            premiumProfit > standardProfit,
            "Premium (lower tax) should yield higher profit",
        )
    }

    // endregion

    // region Favorites

    @Test
    fun saveToFavorites_addsFavoriteAndPersists() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"

        vm.saveToFavorites()
        advanceUntilIdle()

        assertEquals(1, vm.favorites.size)
        assertEquals("Healing Potion", vm.favorites[0].potionName)

        // Verify persisted to repository
        val persisted = fakeFavoritesRepo.loadFavorites()
        assertEquals(1, persisted.size)
    }

    @Test
    fun saveToFavorites_duplicateNotAdded() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"

        vm.saveToFavorites()
        vm.saveToFavorites()
        advanceUntilIdle()

        assertEquals(1, vm.favorites.size)
    }

    @Test
    fun removeFromFavorites_removesAndPersists() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"

        vm.saveToFavorites()
        advanceUntilIdle()
        assertEquals(1, vm.favorites.size)

        vm.removeFromFavorites(vm.favorites[0])
        advanceUntilIdle()

        assertEquals(0, vm.favorites.size)
        val persisted = fakeFavoritesRepo.loadFavorites()
        assertEquals(0, persisted.size)
    }

    @Test
    fun applyFavorite_restoresAllSelections() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        val recipe = FavoriteRecipe(
            potionName = "Energy Potion",
            tier = "T6",
            enchantment = 2,
            city = "Brecilien",
        )
        vm.applyFavorite(recipe)

        assertEquals("Energy Potion", vm.selectedPotion)
        assertEquals("T6", vm.selectedTier)
        assertEquals("Outstanding (.2)", vm.selectedEnchantment)
        assertEquals("Brecilien", vm.selectedCity)
    }

    // endregion

    // region Server Selection

    @Test
    fun updateServer_persistsToRepository() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        vm.updateServer("North America")
        advanceUntilIdle()

        assertEquals("North America", vm.selectedServer)
        assertEquals("North America", fakeFavoritesRepo.loadSelectedServer())
    }

    // endregion

    // region Price Fetching

    @Test
    fun fetchPricesForCurrentRecipe_success_populatesPrices() = runTest {
        fakeRepository.nextResult = ApiResult.Success(
            listOf(
                MarketItemPrice(
                    item_id = "T2_AGARIC",
                    city = "Caerleon",
                    sell_price_min = 100,
                    buy_price_max = 80,
                ),
                MarketItemPrice(
                    item_id = "T2_POTION_HEAL",
                    city = "Caerleon",
                    sell_price_min = 500,
                    buy_price_max = 400,
                ),
            )
        )
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"

        vm.fetchPricesForCurrentRecipe()
        advanceUntilIdle()

        assertEquals("100", vm.ingredientPrices["T2_AGARIC"])
        assertEquals("500", vm.potionSellPrice)
        assertNull(vm.networkError)
    }

    @Test
    fun fetchPricesForCurrentRecipe_networkError_setsErrorMessage() = runTest {
        fakeRepository.nextResult = ApiResult.Error(RuntimeException("Network timeout"))
        val vm = createViewModel()
        advanceUntilIdle()

        vm.onPotionSelected("Healing Potion")
        advanceUntilIdle()
        vm.selectedCity = "Caerleon"

        vm.fetchPricesForCurrentRecipe()
        advanceUntilIdle()

        assertNotNull(vm.networkError)
        assertTrue(vm.networkError?.contains("Network timeout") == true)
    }

    // endregion
}

// region Fakes

/**
 * Fake repository for testing — returns a configurable response.
 */
class FakeAlbionMarketRepository : AlbionMarketRepository {
    var nextResult: ApiResult<List<MarketItemPrice>> = ApiResult.Success(emptyList())

    override suspend fun getPrices(
        itemIds: List<String>,
        city: String,
        serverCode: String,
    ): ApiResult<List<MarketItemPrice>> = nextResult

    override suspend fun getPricesMultiCity(
        itemIds: List<String>,
        cities: List<String>,
        serverCode: String,
    ): ApiResult<List<MarketItemPrice>> = nextResult
}

// endregion
