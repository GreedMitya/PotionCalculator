package com.greedmitya.albcalculator.storage

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetailsResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import com.greedmitya.albcalculator.domain.AppPremiumRepository
import com.greedmitya.albcalculator.domain.AppPurchaseResult
import com.greedmitya.albcalculator.BuildConfig
import io.github.aakira.napier.Napier
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume

/**
 * Google Play Billing implementation of [AppPremiumRepository].
 * Handles one-time purchase of "Craft+" premium, restorable after reinstall.
 */
class GooglePlayPremiumRepository(
    private val context: Context,
) : AppPremiumRepository {

    companion object {
        const val PREMIUM_PRODUCT_ID = "craft_plus_premium"
    }

    private var pendingPurchaseCallback: ((AppPurchaseResult) -> Unit)? = null

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        val callback = pendingPurchaseCallback
        pendingPurchaseCallback = null

        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                Napier.d("Purchases updated successfully")
                val purchased = purchases?.any { purchase ->
                    purchase.products.contains(PREMIUM_PRODUCT_ID) &&
                        purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                } ?: false
                // Acknowledge unacknowledged purchases to prevent auto-refund
                purchases?.filter { purchase ->
                    purchase.products.contains(PREMIUM_PRODUCT_ID) &&
                        purchase.purchaseState == Purchase.PurchaseState.PURCHASED &&
                        !purchase.isAcknowledged
                }?.forEach { purchase ->
                    acknowledgePurchase(purchase)
                }
                callback?.invoke(
                    if (purchased) AppPurchaseResult.Success else AppPurchaseResult.Error("Purchase not completed"),
                )
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                Napier.i("User cancelled the purchase flow")
                callback?.invoke(AppPurchaseResult.UserCancelled)
            }
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                Napier.i("Item already owned")
                callback?.invoke(AppPurchaseResult.AlreadyOwned)
            }
            else -> {
                Napier.e("Billing error: ${billingResult.responseCode} - ${billingResult.debugMessage}")
                callback?.invoke(
                    AppPurchaseResult.Error("Billing error: ${billingResult.debugMessage}"),
                )
            }
        }
    }

    private val billingClient: BillingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases(
            com.android.billingclient.api.PendingPurchasesParams.newBuilder()
                .enableOneTimeProducts()
                .build(),
        )
        .build()

    private suspend fun ensureConnected(): Boolean {
        if (billingClient.isReady) return true

        return suspendCancellableCoroutine { continuation ->
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    val isSuccess = billingResult.responseCode == BillingClient.BillingResponseCode.OK
                    Napier.d("Billing setup finished: $isSuccess (code: ${billingResult.responseCode})")
                    if (continuation.isActive) {
                        continuation.resume(isSuccess)
                    }
                }

                override fun onBillingServiceDisconnected() {
                    Napier.w("Billing service disconnected")
                }
            })
        }
    }

    override suspend fun isPremiumUnlocked(): Boolean {
        if (BuildConfig.DEBUG) {
            Napier.d("DEBUG build: premium auto-unlocked for testing")
            return true
        }

        if (!ensureConnected()) return false

        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        val result = billingClient.queryPurchasesAsync(params)
        val validPurchases = result.purchasesList.filter { purchase ->
            purchase.products.contains(PREMIUM_PRODUCT_ID) &&
                purchase.purchaseState == Purchase.PurchaseState.PURCHASED
        }
        // Acknowledge any unacknowledged purchases (recovery after reinstall/crash)
        validPurchases.filter { !it.isAcknowledged }.forEach { purchase ->
            acknowledgePurchase(purchase)
        }
        return validPurchases.isNotEmpty()
    }

    override suspend fun purchasePremium(activity: Any): AppPurchaseResult {
        if (activity !is Activity) {
            return AppPurchaseResult.Error("Activity reference required for purchase flow")
        }

        if (!ensureConnected()) {
            return AppPurchaseResult.Error("Could not connect to Google Play")
        }

        val productDetails = queryProductDetails()
            ?: return AppPurchaseResult.Error("Product not found in Play Store")

        val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(productDetails)
            .build()

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productDetailsParams))
            .build()

        return try {
            withTimeout(120_000) {
                suspendCancellableCoroutine { continuation ->
                    pendingPurchaseCallback = { result ->
                        if (continuation.isActive) {
                            continuation.resume(result)
                        }
                    }
                    val launchResult = billingClient.launchBillingFlow(activity, billingFlowParams)
                    if (launchResult.responseCode != BillingClient.BillingResponseCode.OK) {
                        pendingPurchaseCallback = null
                        if (continuation.isActive) {
                            continuation.resume(
                                AppPurchaseResult.Error("Failed to launch billing: ${launchResult.debugMessage}"),
                            )
                        }
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            pendingPurchaseCallback = null
            Napier.e("Purchase flow timed out after 2 minutes")
            AppPurchaseResult.Error("Purchase timed out — please try again")
        }
    }

    override suspend fun restorePurchases(): AppPurchaseResult {
        return if (isPremiumUnlocked()) {
            AppPurchaseResult.Success
        } else {
            AppPurchaseResult.Error("No previous purchase found")
        }
    }

    override suspend fun getFormattedPrice(): String? {
        if (!ensureConnected()) return null
        return queryProductDetails()?.oneTimePurchaseOfferDetails?.formattedPrice
    }

    private suspend fun queryProductDetails(): com.android.billingclient.api.ProductDetails? {
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PREMIUM_PRODUCT_ID)
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
        )

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        val result: ProductDetailsResult = billingClient.queryProductDetails(params)
        val product = result.productDetailsList?.firstOrNull()
        if (product == null) {
            Napier.e("Product $PREMIUM_PRODUCT_ID not found in Play Store. Response code: ${result.billingResult.responseCode}")
        } else {
            Napier.d("Product $PREMIUM_PRODUCT_ID found: ${product.name}")
        }
        return product
    }

    /**
     * Acknowledges a purchase so Google Play does not auto-refund it after 3 days.
     * Required for all non-consumable in-app purchases.
     */
    private fun acknowledgePurchase(purchase: Purchase) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params) { result ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                Napier.d("Purchase acknowledged successfully")
            } else {
                Napier.e("Failed to acknowledge purchase: ${result.responseCode} - ${result.debugMessage}")
            }
        }
    }

    /** Release billing connection when no longer needed. */
    fun destroy() {
        if (billingClient.isReady) {
            billingClient.endConnection()
            Napier.d("BillingClient connection ended")
        }
    }
}
