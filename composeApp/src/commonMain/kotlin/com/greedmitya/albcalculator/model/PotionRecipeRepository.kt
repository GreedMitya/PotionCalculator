package com.greedmitya.albcalculator.model

/**
 * Recipe ingredients indexed by: potionBaseId -> tier -> enchantmentIndex -> ingredients.
 * Data loaded from recipes.json at first access (lazy).
 *
 * Previously this was a 1,128-line hardcoded Map. Now it's a thin delegation to [PotionRecipeLoader].
 */
val potionIngredientsByTierAndEnchant: Map<String, Map<String, Map<Int, List<Ingredient>>>>
    get() = PotionRecipeLoader.ingredientsByTierAndEnchant
