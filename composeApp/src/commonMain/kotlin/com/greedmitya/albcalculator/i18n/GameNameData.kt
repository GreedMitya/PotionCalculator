package com.greedmitya.albcalculator.i18n

import kotlinx.serialization.Serializable

/**
 * Parsed structure of a game_names/{lang}.json file.
 *
 * Each map key is the canonical English identifier:
 *  - ingredients: Albion item ID (e.g. "T2_AGARIC")
 *  - potions:     English display name (e.g. "Healing Potion")
 *  - cities:      English city name   (e.g. "Caerleon")
 */
@Serializable
data class GameNameData(
    val ingredients: Map<String, String> = emptyMap(),
    val potions: Map<String, String> = emptyMap(),
    val cities: Map<String, String> = emptyMap(),
)
