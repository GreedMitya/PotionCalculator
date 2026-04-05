package com.greedmitya.albcalculator.model

data class BottomNavItemData(
    val label: String,
    val iconResId: Int,
    val selectedIconResId: Int? = null,
    /** Show the label as a text composable below the icon (for icons without built-in text). */
    val showExternalLabel: Boolean = false,
)

