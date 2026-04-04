package com.greedmitya.albcalculator.logic

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Singleton state holder to manage the user's Premium Status across the entire app.
 * Integrated with RevenueCat later.
 */
object PremiumManager {
    // Временно заглушка: пока ставим false (для разработки можно переключать в true)
    private val _isPremium = MutableStateFlow(false)
    val isPremium: StateFlow<Boolean> = _isPremium.asStateFlow()

    fun debugSetPremium(hasAccess: Boolean) {
        _isPremium.value = hasAccess
    }
    
    // Здесь в будущем будут функции инициализации Purchases (RevenueCat)
    // fun initializePurchases() { ... }
}
