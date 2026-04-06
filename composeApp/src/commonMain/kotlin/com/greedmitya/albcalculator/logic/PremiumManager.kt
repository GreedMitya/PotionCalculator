package com.greedmitya.albcalculator.logic

/**
 * APP-LEVEL premium subscription manager.
 *
 * IMPORTANT: This is NOT the same as the in-game "Premium" toggle in the calculator.
 * - **In-game premium** (`CraftViewModel.isPremium`): Albion Online subscription status
 *   that affects market tax rates (6.5% vs 10.5%). Users toggle this themselves.
 * - **App premium** (this class): App purchase that gates premium-only features
 *   like City Price Monitor, Potion Advisor, etc.
 *
 * Premium entitlement is managed by [GooglePlayPremiumRepository] via Google Play Billing.
 * This file exists only as documentation of the two-premium concept.
 */
object PremiumManager
