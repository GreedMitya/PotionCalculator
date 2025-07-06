package com.greedmitya.albcalculator.model
// shared/src/commonMain/kotlin/com/greedmitya/albcalculator/components/BottomNavItemData.kt

data class BottomNavItemData(
    val label: String,
    val iconResId: Int,
    val selectedIconResId: Int? = null // ← добавим опционально
)

