package com.greedmitya.albcalculator.logic

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * APP-LEVEL premium subscription manager (future RevenueCat / Google Billing integration).
 *
 * IMPORTANT: This is NOT the same as the in-game "Premium" toggle in the calculator.
 * - **In-game premium** (`CraftViewModel.isPremium`): Albion Online subscription status
 *   that affects market tax rates (6.5% vs 10.5%). Users toggle this themselves.
 * - **App premium** (this class): App subscription that will gate premium-only features
 *   like Global Market Monitor, unlimited favorites, etc. Managed via purchase verification.
 *
 * TODO: Integrate with RevenueCat or Google Billing for real entitlement checks.
 *       The [debugSetPremium] method is for development only and must be removed before release.
 */
object PremiumManager {
    private val _isAppPremium = MutableStateFlow(false)
    val isAppPremium: StateFlow<Boolean> = _isAppPremium.asStateFlow()

    /**
     * Development-only setter. Must be replaced with real purchase verification before release.
     * Do NOT expose this to the UI — app premium must be derived from verified purchases.
     */
    internal fun debugSetPremium(hasAccess: Boolean) {
        _isAppPremium.value = hasAccess
    }
}
