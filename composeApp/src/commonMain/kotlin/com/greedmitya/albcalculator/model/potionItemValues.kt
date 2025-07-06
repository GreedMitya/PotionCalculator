package com.greedmitya.albcalculator.model

val potionItemValues: Map<String, Map<String, Int>> = mapOf(
    "POTION_HEAL" to mapOf("T2" to 320, "T4" to 1200,"T6" to 4300),
    "POTION_ENERGY" to mapOf("T2" to 320, "T4" to 1200,"T6" to 4300),
    "POTION_REVIVE" to mapOf("T3" to 320,"T5" to 1700,"T7" to 5800),
    "POTION_STONESKIN" to mapOf("T3" to 320,"T5" to 1700,"T7" to 7200),
    "POTION_COOLDOWN" to mapOf("T3" to 480,"T5" to 2200,"T7" to 7200),
    "POTION_CLEANSE" to mapOf("T8" to 7200),
    "POTION_SLOWFIELD" to mapOf("T3" to 320,"T5" to 1700,"T7" to 7200),
    "ALCOHOL" to mapOf("T6" to 960, "T7" to 1120, "T8" to 1280),
    "POTION_ACID" to mapOf("T3" to 640,"T5" to 3400,"T7" to 14400),
    "POTION_BERSERK" to mapOf("T4" to 640,"T6" to 3400,"T8" to 14400),
    "POTION_MOB_RESET" to mapOf("T3" to 640,"T5" to 3400,"T7" to 14400),
    "POTION_CLEANSE2" to mapOf("T3" to 640,"T5" to 3400,"T7" to 14400),
    "POTION_LAVA" to mapOf("T4" to 640,"T6" to 3400,"T8" to 14400),
    "POTION_GATHER" to mapOf("T4" to 640,"T6" to 3400,"T8" to 14400),
    "POTION_TORNADO" to mapOf("T4" to 640,"T6" to 3400,"T8" to 14400),
)