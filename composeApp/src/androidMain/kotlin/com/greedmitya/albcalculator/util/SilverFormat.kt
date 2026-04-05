package com.greedmitya.albcalculator.util

/**
 * Formats a silver amount into a compact human-readable string.
 * Examples: 1_000_000 → "1.0m", 1_575_000 → "1.575m", 157_500 → "157.5k", 900 → "900"
 */
fun formatSilver(amount: Double): String {
    if (amount < 0) return "-${formatSilver(-amount)}"
    return when {
        amount >= 1_000_000 -> {
            val raw = "%.3f".format(amount / 1_000_000.0).trimEnd('0').trimEnd('.')
            val withDecimal = if ('.' in raw) raw else "$raw.0"
            "${withDecimal}m"
        }
        amount >= 1_000 -> "%.1fk".format(amount / 1_000.0)
        else -> "%.0f".format(amount)
    }
}
