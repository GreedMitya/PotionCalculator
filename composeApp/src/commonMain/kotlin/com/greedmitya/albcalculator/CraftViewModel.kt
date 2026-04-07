package com.greedmitya.albcalculator

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greedmitya.albcalculator.domain.AppPremiumRepository
import com.greedmitya.albcalculator.domain.AppPurchaseResult
import com.greedmitya.albcalculator.domain.CalculateProfitUseCase
import com.greedmitya.albcalculator.domain.FavoritesRepository
import com.greedmitya.albcalculator.domain.FetchPricesUseCase
import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.model.FavoriteRecipe
import com.greedmitya.albcalculator.model.Ingredient
import com.greedmitya.albcalculator.model.PotionCraftResult
import com.greedmitya.albcalculator.model.PotionInfo
import com.greedmitya.albcalculator.model.PotionRecipeLoader
import com.greedmitya.albcalculator.model.potionIngredientsByTierAndEnchant
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import potioncalculator.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
class CraftViewModel(
    private val fetchPricesUseCase: FetchPricesUseCase,
    private val calculateProfitUseCase: CalculateProfitUseCase,
    private val favoritesRepository: FavoritesRepository,
    private val appPremiumRepository: AppPremiumRepository,
) : ViewModel() {

    companion object {
        /** Delay to allow Compose to flush state before validation triggers */
        private const val VALIDATION_DEBOUNCE_MS = 10L
        /** Duration error fields blink before auto-clearing */
        private const val ERROR_BLINK_DURATION_MS = 2000L
        /** Sequential animation delays for price reset choreography */
        private const val RESET_CLEAR_DELAY_MS = 50L
        private const val RESET_SHIMMER_SHOW_DELAY_MS = 100L
        private const val RESET_SHIMMER_HIDE_DELAY_MS = 400L
    }

    var networkError by mutableStateOf<String?>(null)
        private set

    /** Whether the user has purchased Craft+ premium (unlocks Craft+, Markets, Advisor). */
    var isAppPremiumUnlocked by mutableStateOf(false)
        private set

    /** Formatted price from Google Play (e.g. "$1.99", "€0,99"). Null while loading or unavailable. */
    var premiumPrice by mutableStateOf<String?>(null)
        private set

    /** True while a purchase or restore call is in flight — disables buttons to prevent double-tap. */
    var isPurchasing by mutableStateOf(false)
        private set

    /** Batch craft quantity for Craft+ mode. */
    var craftQuantity by mutableStateOf("1")
    val craftQuantityInt: Int get() = craftQuantity.toIntOrNull()?.coerceAtLeast(1) ?: 1

    val favorites = mutableStateListOf<FavoriteRecipe>()
    val allPotions = listOf(
        PotionInfo("Healing Potion", "POTION_HEAL", listOf("T2", "T4", "T6")),
        PotionInfo("Energy Potion", "POTION_ENERGY", listOf("T2", "T4", "T6")),
        PotionInfo("Gigantify Potion", "POTION_REVIVE", listOf("T3", "T5", "T7")),
        PotionInfo("Resistance Potion", "POTION_STONESKIN", listOf("T3", "T5", "T7")),
        PotionInfo("Poison Potion", "POTION_COOLDOWN", listOf("T4", "T6", "T8")),
        PotionInfo("Invisibility Potion", "POTION_CLEANSE", listOf("T8")),
        PotionInfo("Sticky Potion", "POTION_SLOWFIELD", listOf("T3", "T5", "T7")),
        PotionInfo("Alcohol", "ALCOHOL", listOf("T6", "T7", "T8"), outputQuantity = 1, hasEnchants = false),
        PotionInfo("Acid Potion", "POTION_ACID", listOf("T3", "T5", "T7"), outputQuantity = 10),
        PotionInfo("Berserk Potion", "POTION_BERSERK", listOf("T4", "T6", "T8"), outputQuantity = 10),
        PotionInfo("Calming Potion", "POTION_MOB_RESET", listOf("T3", "T5", "T7"), outputQuantity = 10),
        PotionInfo("Cleansing Potion", "POTION_CLEANSE2", listOf("T3", "T5", "T7"), outputQuantity = 10),
        PotionInfo("Hellfire Potion", "POTION_LAVA", listOf("T4", "T6", "T8"), outputQuantity = 10),
        PotionInfo("Gathering Potion", "POTION_GATHER", listOf("T4", "T6", "T8"), outputQuantity = 10),
        PotionInfo("Tornado in a Bottle", "POTION_TORNADO", listOf("T4", "T6", "T8"), outputQuantity = 10),
    )

    val potions: List<String> get() = allPotions.map { it.displayName }
    val enchantments = listOf("Normal (.0)", "Good (.1)", "Outstanding (.2)", "Excellent (.3)")

    /** Enchantment options filtered for the selected potion — Alcohol only has Normal (.0). */
    val availableEnchantments: List<String>
        get() = if (selectedPotionInfo?.hasEnchants == false) listOf("Normal (.0)") else enchantments
    val cities = listOf("Caerleon", "Bridgewatch", "Martlock", "Lymhurst", "Fort Sterling", "Thetford", "Brecilien")

    var selectedPotion by mutableStateOf<String?>(null)
    var selectedTier by mutableStateOf<String?>(null)
    var selectedEnchantment by mutableStateOf<String?>(null)
    var selectedCity by mutableStateOf<String?>(null)

    var feePerNutritionInput by mutableStateOf("")
    var potionSellPrice by mutableStateOf("")
    var isPremium by mutableStateOf(false)
    var useFocus by mutableStateOf(false)
    var focusBasic by mutableStateOf("")
    var focusMastery by mutableStateOf("")
    var focusTotal by mutableStateOf("")

    var blinkingErrorFields by mutableStateOf(setOf<String>())
        private set

    val ingredientPrices = mutableStateMapOf<String, String>()
    private var resultInternal by mutableStateOf<PotionCraftResult?>(null)
    val result: PotionCraftResult? get() = resultInternal
    val shimmerColor = mutableStateOf(Color.Transparent)

    val selectedPotionInfo: PotionInfo? get() = allPotions.find { it.displayName == selectedPotion }
    val availableTiers: List<String> get() = selectedPotionInfo?.availableTiers ?: emptyList()
    val outputQuantity: Int get() = selectedPotionInfo?.outputQuantity ?: 5

    var blinkIngredients by mutableStateOf(setOf<String>())
    var isBlinkingResult by mutableStateOf(false)

    var validateForCalculate by mutableStateOf(false)
    var validateForMarket by mutableStateOf(false)
    val serverDisplayNames = mapOf(
        "europe" to "Europe",
        "west" to "North America",
        "east" to "Asia",
    )
    var selectedServer by mutableStateOf("Europe")
        private set

    init {
        viewModelScope.launch {
            // Load recipe data first — everything else depends on it
            try {
                val jsonBytes = Res.readBytes("files/recipes.json")
                PotionRecipeLoader.initializeWith(jsonBytes.decodeToString())
            } catch (e: Exception) {
                Napier.e("Failed to load recipes.json from resources", e)
            }

            // Load persisted user preferences
            val saved = favoritesRepository.loadFavorites()
            favorites.addAll(saved)
            val storedServer = favoritesRepository.loadSelectedServer()
            selectedServer = storedServer

            // Check app premium status and fetch regional price for the upgrade screen
            isAppPremiumUnlocked = appPremiumRepository.isPremiumUnlocked()
            premiumPrice = appPremiumRepository.getFormattedPrice()
        }
    }

    fun updateServer(newValue: String) {
        selectedServer = newValue
        viewModelScope.launch {
            favoritesRepository.saveSelectedServer(newValue)
        }
    }

    // region Validation

    val isPotionError get() = (validateForCalculate || validateForMarket) && selectedPotion == null
    val isTierError get() = (validateForCalculate || validateForMarket) && selectedTier == null
    val isEnchantmentError get() = (validateForCalculate || validateForMarket) && selectedEnchantment == null
    val isCityError get() = (validateForCalculate || validateForMarket) && selectedCity == null
    val isFeeError get() = validateForCalculate && feePerNutritionInput.toDoubleOrNull() == null
    val isSellPriceError get() = validateForCalculate && potionSellPrice.toDoubleOrNull() == null

    fun triggerValidationForCalculate() {
        validateForMarket = false
        validateForCalculate = false
        viewModelScope.launch {
            delay(VALIDATION_DEBOUNCE_MS)
            validateForCalculate = true
        }
    }

    fun triggerValidationForMarket() {
        validateForCalculate = false
        validateForMarket = false
        viewModelScope.launch {
            delay(VALIDATION_DEBOUNCE_MS)
            validateForMarket = true
        }
    }

    fun triggerBlinkForErrors(type: String) {
        val invalidFields = mutableSetOf<String>()

        if (selectedPotion == null) invalidFields.add("potion")
        if (selectedTier == null) invalidFields.add("tier")
        if (selectedEnchantment == null) invalidFields.add("enchant")
        if (selectedCity == null) invalidFields.add("city")

        if (type == "calculate") {
            if (feePerNutritionInput.toDoubleOrNull() == null) invalidFields.add("fee")
            if (potionSellPrice.toDoubleOrNull() == null) invalidFields.add("sell")

            val ingredients = getRecipeForSelected()
            ingredients.forEach { ingredient ->
                if (ingredientPrices[ingredient.name]?.toDoubleOrNull() == null) {
                    invalidFields.add("ingredient_${ingredient.name}")
                }
            }
        }

        blinkingErrorFields = invalidFields

        viewModelScope.launch {
            delay(ERROR_BLINK_DURATION_MS)
            blinkingErrorFields = emptySet()
        }
    }

    fun isIngredientError(ingredientId: String): Boolean {
        val value = ingredientPrices[ingredientId]?.toDoubleOrNull()
        return validateForCalculate && value == null
    }

    // endregion

    // region Recipe & Calculation

    fun onPotionSelected(potionName: String) {
        selectedPotion = potionName
        selectedEnchantment = "Normal (.0)"
        selectedTier = selectedPotionInfo?.availableTiers?.firstOrNull()
        resetPrices()
    }

    fun getFullItemId(): String? {
        val tier = selectedTier ?: return null
        val enchantIndex = enchantments.indexOf(selectedEnchantment ?: "Normal (.0)").coerceAtLeast(0)
        return selectedPotionInfo?.getFullItemId(tier, enchantIndex)
    }

    fun getRecipeForSelected(): List<Ingredient> {
        val baseId = selectedPotionInfo?.baseId ?: return emptyList()
        val tier = selectedTier ?: return emptyList()
        val enchant = enchantments.indexOf(selectedEnchantment ?: "Normal (.0)").coerceAtLeast(0)
        return potionIngredientsByTierAndEnchant[baseId]?.get(tier)?.get(enchant) ?: emptyList()
    }

    fun calculateProfit() {
        resultInternal = calculateProfitUseCase.execute(
            ingredients = getRecipeForSelected(),
            ingredientPrices = ingredientPrices,
            baseId = selectedPotionInfo?.baseId,
            selectedTier = selectedTier,
            feePerNutritionInput = feePerNutritionInput,
            useFocus = useFocus,
            isPremium = isPremium,
            focusBasic = focusBasic,
            focusMastery = focusMastery,
            focusTotal = focusTotal,
            selectedCity = selectedCity,
            potionSellPrice = potionSellPrice,
            outputQuantity = outputQuantity,
            craftQuantity = craftQuantityInt,
        )
    }

    fun resetPrices() {
        val ingredientNames = getRecipeForSelected().map { it.name }.toSet()
        blinkIngredients = ingredientNames
        isBlinkingResult = true
        viewModelScope.launch {
            delay(RESET_CLEAR_DELAY_MS)
            ingredientPrices.clear()
            potionSellPrice = ""
            resultInternal = null
            shimmerColor.value = Color(0xFFCCCCCC)
            delay(RESET_SHIMMER_SHOW_DELAY_MS)
            shimmerColor.value = Color.Transparent
            delay(RESET_SHIMMER_HIDE_DELAY_MS)
            blinkIngredients = emptySet()
            isBlinkingResult = false
        }
    }

    val isReadyToCalculate: Boolean get() {
        val hasBasicInfo = selectedPotion != null && selectedTier != null && selectedEnchantment != null && selectedCity != null
        val hasFee = feePerNutritionInput.toDoubleOrNull() != null
        val hasSellPrice = potionSellPrice.toDoubleOrNull() != null
        val allIngredientsHavePrices = getRecipeForSelected().all {
            ingredientPrices[it.name]?.toDoubleOrNull() != null
        }
        return hasBasicInfo && hasFee && hasSellPrice && allIngredientsHavePrices
    }

    val isReadyForMarket: Boolean get() =
        selectedPotion != null && selectedTier != null && selectedEnchantment != null && selectedCity != null

    // endregion

    // region Price Fetching (delegated to FetchPricesUseCase)

    fun fetchPricesForCurrentRecipe() {
        val ingredients = getRecipeForSelected()
        val potionId = getFullItemId() ?: return
        val city = selectedCity ?: return
        val itemIds = (ingredients.map { it.name } + potionId).distinct()
        val serverCode = serverDisplayNames.entries.firstOrNull { it.value == selectedServer }?.key ?: "europe"

        potionSellPrice = ""
        ingredientPrices.clear()

        viewModelScope.launch {
            val result = fetchPricesUseCase.execute(
                itemIds = itemIds,
                city = city,
                serverCode = serverCode,
                potionItemId = potionId,
            )
            when (result) {
                is ApiResult.Success -> {
                    result.data.ingredientPrices.forEach { (id, price) ->
                        ingredientPrices[id] = price
                    }
                    result.data.potionSellPrice?.let { potionSellPrice = it }
                }
                is ApiResult.Error -> {
                    networkError = "API Error: ${result.exception.message}"
                    Napier.e("Network fetch failed", result.exception)
                }
                ApiResult.Loading -> {
                    networkError = null
                }
            }
        }
    }

    // endregion

    // region Favorites (delegated to FavoritesRepository)

    fun saveToFavorites() {
        val new = FavoriteRecipe(
            potionName = selectedPotion ?: return,
            tier = selectedTier ?: return,
            city = selectedCity ?: return,
            enchantment = enchantments.indexOf(selectedEnchantment ?: "Normal (.0)"),
            feePerNutrition = feePerNutritionInput,
            ingredientPrices = ingredientPrices.toMap(),
        )
        // Compare by identity (potion+tier+enchant+city) so updated prices can be re-saved
        val existingIndex = favorites.indexOfFirst {
            it.potionName == new.potionName &&
                it.tier == new.tier &&
                it.enchantment == new.enchantment &&
                it.city == new.city
        }
        if (existingIndex >= 0) {
            favorites[existingIndex] = new  // update prices/fee in existing slot
        } else {
            favorites.add(new)
        }
        persistFavorites()
    }

    fun removeFromFavorites(recipe: FavoriteRecipe) {
        favorites.remove(recipe)
        persistFavorites()
    }

    fun applyFavorite(recipe: FavoriteRecipe) {
        selectedPotion = recipe.potionName
        selectedTier = recipe.tier
        selectedEnchantment = enchantments.getOrNull(recipe.enchantment)
        selectedCity = recipe.city
        feePerNutritionInput = recipe.feePerNutrition
        // Restore saved prices; clear any leftover prices from a different recipe first
        ingredientPrices.clear()
        recipe.ingredientPrices.forEach { (id, price) ->
            ingredientPrices[id] = price
        }
        // Clear stale result and sell price — user should recalculate
        potionSellPrice = ""
        resultInternal = null
    }

    private fun persistFavorites() {
        viewModelScope.launch {
            favoritesRepository.saveFavorites(favorites.toList())
        }
    }

    // endregion

    // region App Premium (Craft+ purchase)

    fun purchasePremium(activity: Any) {
        if (isPurchasing) return
        viewModelScope.launch {
            isPurchasing = true
            when (val result = appPremiumRepository.purchasePremium(activity)) {
                is AppPurchaseResult.Success,
                is AppPurchaseResult.AlreadyOwned -> {
                    isAppPremiumUnlocked = true
                }
                is AppPurchaseResult.Error -> {
                    networkError = result.message
                }
                is AppPurchaseResult.UserCancelled -> {
                    // No action needed
                }
            }
            isPurchasing = false
        }
    }

    fun restorePurchases() {
        if (isPurchasing) return
        viewModelScope.launch {
            isPurchasing = true
            when (val result = appPremiumRepository.restorePurchases()) {
                is AppPurchaseResult.Success,
                is AppPurchaseResult.AlreadyOwned -> {
                    isAppPremiumUnlocked = true
                }
                is AppPurchaseResult.Error -> {
                    networkError = result.message
                }
                is AppPurchaseResult.UserCancelled -> {
                    // No action needed
                }
            }
            isPurchasing = false
        }
    }

    // endregion

    fun clearNetworkError() {
        networkError = null
    }

    /**
     * FOR DEBUG ONLY — overrides premium state for local testing without a real purchase.
     * The only call site is inside a BuildConfig.DEBUG guard in SettingsScreen, so this
     * method is never reachable in release builds.
     *
     * When locking: if no real price was loaded from Play, injects a fake price so the
     * PriceBadge is visible during UI testing.
     */
    fun debugOverridePremiumState(unlocked: Boolean) {
        isAppPremiumUnlocked = unlocked
        if (!unlocked && premiumPrice == null) {
            premiumPrice = "$ 1.99"
        }
    }
}
