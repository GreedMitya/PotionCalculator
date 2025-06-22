package com.greedmitya.albcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CraftViewModel : ViewModel() {

    // Выбранные параметры
    var selectedPotion by mutableStateOf<String?>(null)
    var selectedTier by mutableStateOf<String?>(null)
    var selectedEnchantment by mutableStateOf<String?>(null)
    var selectedCity by mutableStateOf<String?>(null)

    // Стоимость за 100 питания
    var feePerNutrition by mutableStateOf(0)

    // Переключатели
    var isPremium by mutableStateOf(false)
    var useFocus by mutableStateOf(false)

    // Результаты расчёта (могут быть строкой или моделью)
    var profitResult by mutableStateOf<String?>(null)

    fun calculateProfit() {
        // Здесь будет формула расчёта прибыли
        profitResult = "12345 silver (пример)"
    }

    fun reset() {
        selectedPotion = null
        selectedTier = null
        selectedEnchantment = null
        selectedCity = null
        feePerNutrition = 0
        isPremium = false
        useFocus = false
        profitResult = null
    }
}