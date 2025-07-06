package com.greedmitya.albcalculator

import Ingredient
import PotionInfo
import PotionRecipe
import PotionCraftCalculator
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.greedmitya.albcalculator.model.PotionCraftResult
import com.greedmitya.albcalculator.model.potionIngredientsByTierAndEnchant
import androidx.lifecycle.viewModelScope
import com.greedmitya.albcalculator.model.FavoriteRecipe
import com.greedmitya.albcalculator.model.potionItemValues
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class CraftViewModel : ViewModel() {
    val favorites = mutableStateListOf<FavoriteRecipe>()
    val allPotions = listOf(
        PotionInfo("Healing Potion", "POTION_HEAL", listOf("T2", "T4", "T6")),
        PotionInfo("Energy Potion", "POTION_ENERGY", listOf("T2", "T4", "T6")),
        PotionInfo("Gigantify Potion", "POTION_REVIVE", listOf("T3", "T5", "T7")),
        PotionInfo("Resistance Potion", "POTION_STONESKIN", listOf("T3", "T5", "T7")),
        PotionInfo("Poison Potion", "POTION_COOLDOWN", listOf("T4", "T6", "T8")),
        PotionInfo("Invisibility Potion", "POTION_CLEANSE", listOf("T8")),
        PotionInfo("Sticky Potion", "POTION_SLOWFIELD", listOf("T3", "T5", "T7")),
        PotionInfo("Alcohol", "ALCOHOL", listOf("T6", "T7", "T8"), outputQuantity = 1),
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
            delay(10)
            validateForCalculate = true
        }
    }

    fun triggerValidationForMarket() {
        validateForCalculate = false
        validateForMarket = false
        viewModelScope.launch {
            delay(10)
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
            delay(2000)
            blinkingErrorFields = emptySet()
        }
    }

    fun isIngredientError(ingredientId: String): Boolean {
        val value = ingredientPrices[ingredientId]?.toDoubleOrNull()
        return validateForCalculate && value == null
    }


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
        val ingredients = getRecipeForSelected().map {
            val price = ingredientPrices[it.name]?.toDoubleOrNull()
            it.copy(price = price)
        }

        val itemValue = selectedPotionInfo?.baseId?.let { baseId ->
            potionItemValues[baseId]?.get(selectedTier ?: "")
        } ?: 0

        resultInternal = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = feePerNutritionInput.toDoubleOrNull() ?: 0.0,
            useFocus = useFocus,
            isPremium = isPremium,
            focusBasic = focusBasic.toDoubleOrNull(),
            focusMastery = focusMastery.toDoubleOrNull(),
            focusTotal = focusTotal.toDoubleOrNull(),
            itemValue = itemValue,
            city = selectedCity,
            sellPrice = potionSellPrice.toDoubleOrNull(),
            outputQuantity = outputQuantity
        )
    }

    fun resetPrices() {
        val ingredientNames = getRecipeForSelected().map { it.name }.toSet()
        blinkIngredients = ingredientNames
        isBlinkingResult = true
        viewModelScope.launch {
            delay(50)
            ingredientPrices.clear()
            potionSellPrice = ""
            resultInternal = null
            shimmerColor.value = Color(0xFFCCCCCC)
            delay(100)
            shimmerColor.value = Color.Transparent
            delay(400)
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

    val isReadyForMarket: Boolean get() = selectedPotion != null && selectedTier != null && selectedEnchantment != null && selectedCity != null

    @Serializable
    data class MarketItemPrice(val item_id: String, val city: String, val sell_price_min: Int, val buy_price_max: Int)

    private val httpClient = HttpClient(CIO)

    fun fetchPricesForCurrentRecipe() {
        val ingredients = getRecipeForSelected()
        val potionId = getFullItemId() ?: return
        val city = selectedCity ?: return
        val itemIds = (ingredients.map { it.name } + potionId).distinct()
        val url = "https://europe.albion-online-data.com/api/v2/stats/prices/${itemIds.joinToString(",")}.json?locations=$city"

        potionSellPrice = ""
        ingredientPrices.clear()

        viewModelScope.launch {
            try {
                val responseText = httpClient.get(url).body<String>()
                val priceData = Json { ignoreUnknownKeys = true }.decodeFromString<List<MarketItemPrice>>(responseText)
                val grouped = priceData.filter { it.city == city }.groupBy { it.item_id }
                grouped.forEach { (id, entries) ->
                    val entry = entries.firstOrNull() ?: return@forEach
                    val sell = entry.sell_price_min.takeIf { it > 0 }
                    val buy = entry.buy_price_max.takeIf { it > 0 }
                    val selectedPrice = when {
                        sell != null && buy != null -> if (sell.toDouble() / buy <= 2.0) sell else null
                        sell != null -> sell
                        buy != null -> (buy * 1.1).toInt()
                        else -> null
                    }
                    selectedPrice?.let { price ->
                        val rounded = price.coerceAtLeast(1)
                        if (id == potionId) potionSellPrice = rounded.toString()
                        else ingredientPrices[id] = rounded.toString()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveToFavorites() {
        val new = FavoriteRecipe(
            potionName = selectedPotion ?: return,
            tier = selectedTier ?: return,
            city = selectedCity ?: return,
            enchantment = enchantments.indexOf(selectedEnchantment ?: "Normal (.0)")
        )
        if (!favorites.contains(new)) {
            favorites.add(new)
        }
    }

    fun removeFromFavorites(recipe: FavoriteRecipe) {
        favorites.remove(recipe)
    }
    fun applyFavorite(recipe: FavoriteRecipe) {
        selectedPotion = recipe.potionName
        selectedTier = recipe.tier
        selectedEnchantment = enchantments.getOrNull(recipe.enchantment)
        selectedCity = recipe.city
    }
    fun loadFavoritesExternally(favs: List<FavoriteRecipe>) {
        favorites.clear()
        favorites.addAll(favs)
    }

    fun currentFavorites(): List<FavoriteRecipe> {
        return favorites.toList()
    }




}
