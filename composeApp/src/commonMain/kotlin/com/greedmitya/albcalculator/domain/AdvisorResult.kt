package com.greedmitya.albcalculator.domain

data class PotionAdvisorResult(
    val potionDisplayName: String,
    val tier: String,
    val enchantment: Int,
    val profitSilver: Double,
    val profitPercent: Double,
    val missingPriceCount: Int,
)

data class AdvisorOutput(
    val topProfitable: List<PotionAdvisorResult>,
    val topForLeveling: List<PotionAdvisorResult>,
    val totalSkipped: Int,
    /** All profitable results sorted by profit % descending — full list, no cap. */
    val allProfitable: List<PotionAdvisorResult> = emptyList(),
    /** All leveling results sorted by profit % descending — full list, no cap. */
    val allForLeveling: List<PotionAdvisorResult> = emptyList(),
)
