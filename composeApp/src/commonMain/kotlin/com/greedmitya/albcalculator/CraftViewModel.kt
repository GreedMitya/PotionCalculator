package com.greedmitya.albcalculator

import Ingredient
import PotionInfo
import PotionRecipe
import PotionCraftCalculator
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.greedmitya.albcalculator.model.PotionCraftResult
import com.greedmitya.albcalculator.model.potionIngredientsByTierAndEnchant

class CraftViewModel : ViewModel() {
    // --- 1) Списки опций для дропдаунов ---
    val allPotions = listOf(
        PotionInfo("Acid Potion", "POTION_ACID", listOf("T3", "T5", "T7")),
        PotionInfo("Berserk Potion", "POTION_BERSERK", listOf("T4", "T6", "T8")),
        PotionInfo("Calming Potion", "POTION_MOB_RESET", listOf("T3", "T5", "T7")),
        PotionInfo("Cleansing Potion", "POTION_CLEANSE2", listOf("T3", "T5", "T7")),
        PotionInfo("Energy Potion", "POTION_ENERGY", listOf("T2", "T4", "T6")),
        PotionInfo("Gigantify Potion", "POTION_REVIVE", listOf("T3", "T5", "T7")),
        PotionInfo("Healing Potion", "POTION_HEAL", listOf("T2", "T4", "T6")),
        PotionInfo("Hellfire Potion", "POTION_LAVA", listOf("T4", "T6", "T8")),
        PotionInfo("Invisibility Potion", "POTION_CLEANSE", listOf("T8")),
        PotionInfo("Resistance Potion", "POTION_STONESKIN", listOf("T3", "T5", "T7")),
        PotionInfo("Poison Potion", "POTION_COOLDOWN", listOf("T4", "T6", "T8")),
        PotionInfo("Sticky Potion", "POTION_SLOWFIELD", listOf("T3", "T5", "T7")),
        PotionInfo("Gathering Yield Potion", "POTION_GATHER", listOf("T4", "T6", "T8")),
        PotionInfo("Tornado in a Bottle", "POTION_TORNADO", listOf("T4", "T6", "T8")),
        PotionInfo("Ale", "POTION_BEER", listOf("T6", "T7", "T8")),
    )

    val potions: List<String>
        get() = allPotions.map { it.displayName }

    val tiers = listOf("T2", "T4", "T5", "T6", "T7", "T8")

    val enchantments = listOf(
        "Normal (.0)",
        "Good (.1)",
        "Outstanding (.2)",
        "Excellent (.3)"
    )

    var selectedPotion by mutableStateOf<String?>(null)
    var selectedTier by mutableStateOf<String?>(null)
    var selectedEnchantment by mutableStateOf<String?>(null)

    val selectedPotionInfo: PotionInfo?
        get() = allPotions.find { it.displayName == selectedPotion }

    val availableTiers: List<String>
        get() = selectedPotionInfo?.availableTiers ?: emptyList()

    fun onPotionSelected(potionName: String) {
        selectedPotion = potionName
        selectedEnchantment = "Normal (.0)"
        selectedTier = selectedPotionInfo?.availableTiers?.firstOrNull()
    }

    // --- Цены и результат ---
    var potionSellPrice by mutableStateOf("")
    val ingredientPrices = mutableStateMapOf<String, String>() // имя → цена

    private var resultInternal by mutableStateOf<PotionCraftResult?>(null)
    val result: PotionCraftResult?
        get() = resultInternal

    val profitSilver: String
        get() = result?.profitSilverFormatted ?: "-/-"

    val profitPercent: String
        get() = result?.profitPercentFormatted ?: "-/-"

    // --- Остальные поля ---
    val cities = listOf("Bridgewatch", "Martlock", "Lymhurst", "Thetford", "Fort Sterling", "Caeroleon", "Brecilien")

    var selectedCity by mutableStateOf<String?>(null)
    var isPremium by mutableStateOf(false)
    var useFocus by mutableStateOf(false)
    var feePerNutritionInput by mutableStateOf("")
    var focusBasic by mutableStateOf("")
    var focusMastery by mutableStateOf("")
    var focusTotal by mutableStateOf("")

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

        val result = PotionCraftCalculator.calculate(
            ingredients = ingredients,
            feePerNutrition = feePerNutritionInput.toDoubleOrNull() ?: 0.0,
            useFocus = useFocus,
            isPremium = isPremium,
            focusBasic = focusBasic.toDoubleOrNull(),
            focusMastery = focusMastery.toDoubleOrNull(),
            focusTotal = focusTotal.toDoubleOrNull()
        ).copy(
            estimatedSellPrice = potionSellPrice.toDoubleOrNull()
        )

        resultInternal = result
    }

    fun reset() {
        selectedPotion = null
        selectedTier = null
        selectedEnchantment = null
        selectedCity = null
        feePerNutritionInput = ""
        isPremium = false
        useFocus = false
        resultInternal = null
        potionSellPrice = ""
        ingredientPrices.clear()
    }
}
