package com.greedmitya.albcalculator.constants

/**
 * Strings that are intentionally NOT in strings.xml and must never be localized.
 *
 * Rule: if a string belongs here, it must have a comment explaining WHY.
 * Three valid reasons:
 *   1. Brand/product name — treated as a proper noun globally (like "Pro", "Premium")
 *   2. Game identifier    — Albion Online's own notation, universal across all language clients
 *   3. Filter sentinel    — internal logic key; display is handled separately via stringResource()
 */

// ── Brand identity ────────────────────────────────────────────────────────────

/**
 * Product tier name used in the sub-tab and the premium badge.
 * Treated as a proper noun — like "Spotify Premium" or "YouTube Pro".
 * Russian/Japanese/Korean players say "Craft+" — it's part of game culture.
 * DO NOT add to strings.xml. DO NOT translate.
 */
const val BRAND_CRAFT_PLUS = "Craft+"
const val BRAND_CRAFT_PLUS_BADGE = "CRAFT+"

// ── Albion Online game notation ───────────────────────────────────────────────

/**
 * Tier abbreviations used by Albion Online across ALL language versions of the game.
 * A Russian player writes "T4" in chat, not "У4". These are game identifiers, not words.
 * DO NOT translate. DO NOT put in strings.xml.
 */
val ALBION_COMPONENT_TIERS = listOf(FILTER_ALL, "T1", "T3", "T4", "T5", "T6", "T7", "T8")
val ALBION_POTION_TIERS    = listOf(FILTER_ALL, "T2", "T3", "T4", "T5", "T6", "T7", "T8")

/**
 * Enchantment level suffixes used by Albion Online in all language clients.
 * ".0" = base, ".1"–".3" = enchanted. Same notation worldwide.
 * DO NOT translate. DO NOT put in strings.xml.
 */
val ALBION_ENCHANT_LEVELS = listOf(FILTER_ALL, ".0", ".1", ".2", ".3")

// ── Filter sentinels ──────────────────────────────────────────────────────────

/**
 * Sentinel value meaning "no filter applied". Used as a key in filter logic:
 *
 *     if (tier == FILTER_ALL) { /* show everything */ }
 *
 * The USER sees a localized string (via displayTransform in SelectorBlock):
 *
 *     val allLabel = stringResource(Res.string.markets_filter_all)
 *     displayTransform = { if (it == FILTER_ALL) allLabel else it }
 *
 * This keeps filter logic deterministic (no locale-dependent comparisons)
 * while still showing localized text in the UI.
 */
const val FILTER_ALL = "All"
