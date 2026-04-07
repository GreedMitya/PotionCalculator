package com.greedmitya.albcalculator.domain

/**
 * Stub implementation for platforms without Google Play Billing (iOS, Desktop)
 * and for unit tests. Premium is always locked.
 */
class InMemoryAppPremiumRepository : AppPremiumRepository {

    override suspend fun isPremiumUnlocked(): Boolean = false

    override suspend fun purchasePremium(activity: Any): AppPurchaseResult =
        AppPurchaseResult.Error("In-app purchases are not supported on this platform")

    override suspend fun restorePurchases(): AppPurchaseResult =
        AppPurchaseResult.Error("In-app purchases are not supported on this platform")

    override suspend fun getFormattedPrice(): String? = null
}
