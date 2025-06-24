package com.greedmitya.albcalculator

import PotionInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.greedmitya.albcalculator.model.SelectorField

class CraftViewModel : ViewModel() {
    // --- 1) Списки опций для дропдаунов ---
    val allPotions = listOf(
        PotionInfo("Acid Potion", "POTION_ACID", listOf("T3", "T5", "T7")), //done
        PotionInfo("Berserker Potion", "POTION_BERSERK",  listOf("T4", "T6", "T8")),//done
        PotionInfo("Calming Potion", "POTION_COOLDOWN", listOf("T3", "T5", "T7")), //done
        PotionInfo("Cleansing Potion", "POTION_CLEANSE", listOf("T3", "T5", "T7")), //done
        PotionInfo("Energy Potion", "POTION_ENERGY", listOf("T2", "T4", "T6")), //done
        PotionInfo("Gigantify Potion", "POTION_GIANT", listOf("T3", "T5", "T7")), //done
        PotionInfo("Healing Potion", "POTION_HEAL", listOf("T2", "T4", "T6")), //done
        PotionInfo("Hellfire Potion", "POTION_HELL",  listOf("T4", "T6", "T8")),//done
        PotionInfo("Invisibility Potion", "POTION_INVISIBILITY", listOf("T8")), //done
        PotionInfo("Resistance Potion", "POTION_RESISTANCE", listOf("T3", "T5", "T7")), //done,
        PotionInfo("Poison Potion", "POTION_SLOWFIELD", listOf("T4", "T6", "T8")),//done
        PotionInfo("Sticky Potion", "POTION_STICKY", listOf("T3", "T5", "T7")), //done
        PotionInfo("Gathering Yield Potion", "POTION_GATHER",  listOf("T4", "T6", "T8")),//done
        PotionInfo("Tornado in a Bottle", "POTION_TORNADO",  listOf("T4", "T6", "T8")),//done
        //PotionInfo("Ale", "POTION_BEER"),
    )

    val potions: List<String>
        get() = allPotions.map { it.displayName }

    val tiers = listOf( "T2", "T4", "T5", "T6", "T7", "T8")

    val enchantments = listOf(
        "Normal (.0)",           // index = 0
        "Good (.1)",       // index = 1
        "Outstanding (.2)",// index = 2
        "Excellent (.3)"   // index = 3
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
        selectedEnchantment = "Normal(.0)" // Автовыбор .0
        selectedTier = selectedPotionInfo?.availableTiers?.firstOrNull()
    }

    fun getFullItemId(): String? {
        val tier = selectedTier ?: return null
        val enchant = selectedEnchantment ?: "Normal"
        val enchantIndex = enchantments.indexOf(enchant).coerceAtLeast(0)
        return selectedPotionInfo?.getFullItemId(tier, enchantIndex)
    }
    val cities = listOf("Bridgewatch", "Martlock", "Lymhurst", "Thetford", "Fort Sterling", "Caeroleon", "Brecilien")

    // --- 2) Состояния выбранных значений ---
    var selectedCity by mutableStateOf<String?>(null)
    // --- 3) Остальные ваши поля и логика ---
    var isPremium by mutableStateOf(false)
    var useFocus by mutableStateOf(false)
    var profitResult by mutableStateOf<String?>(null)

    // Стоимость за 100 питания
    var feePerNutritionInput by mutableStateOf("")
    fun calculateProfit() {
        // Здесь будет формула расчёта прибыли
        profitResult = "12345 silver (пример)"
    }
    var focusBasic by mutableStateOf("")
    var focusMastery by mutableStateOf("")
    var focusTotal by mutableStateOf("")

    fun reset() {
        selectedPotion = null
        selectedTier = null
        selectedEnchantment = null
        selectedCity = null
        feePerNutritionInput = ""
        isPremium = false
        useFocus = false
        profitResult = null
    }
}