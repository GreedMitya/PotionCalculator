package com.greedmitya.albcalculator.domain

/**
 * Abstraction for app-level premium (Craft+) purchase state.
 *
 * NOTE: This is NOT the same as "Albion Online Premium" (the in-game subscription
 * that affects market tax). This controls app feature gating (Craft+, Markets, Advisor).
 *
 * Platform-specific implementations live in androidMain (Google Play Billing).
 * Test / iOS / Desktop stubs use [InMemoryAppPremiumRepository].
 */
interface AppPremiumRepository {
    /** Check whether the user has purchased Craft+ premium. */
    suspend fun isPremiumUnlocked(): Boolean

    /**
     * Launch the purchase flow for Craft+ premium.
     * @param activity Platform activity reference (Activity on Android). Typed as Any
     *                 to keep this interface in commonMain.
     */
    suspend fun purchasePremium(activity: Any): AppPurchaseResult

    /** Re-query past purchases to restore premium after reinstall. */
    suspend fun restorePurchases(): AppPurchaseResult

    /** Returns the Play Store formatted price string (e.g. "$1.99", "€0,99"). Null if unavailable. */
    suspend fun getFormattedPrice(): String?
}

/** Result of a premium purchase or restore attempt. */
sealed interface AppPurchaseResult {
    data object Success : AppPurchaseResult
    data object AlreadyOwned : AppPurchaseResult
    data class Error(val message: String) : AppPurchaseResult
    data object UserCancelled : AppPurchaseResult
}
