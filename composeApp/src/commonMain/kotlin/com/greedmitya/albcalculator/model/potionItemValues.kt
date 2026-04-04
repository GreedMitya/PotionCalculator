package com.greedmitya.albcalculator.model

/**
 * Item value lookup indexed by: potionBaseId -> tier -> crafting item value.
 * Used for calculating crafting tax.
 * Data loaded from recipes.json at first access (lazy).
 *
 * Previously this was a hardcoded Map. Now it's a thin delegation to [PotionRecipeLoader].
 */
val potionItemValues: Map<String, Map<String, Int>>
    get() = PotionRecipeLoader.itemValues
