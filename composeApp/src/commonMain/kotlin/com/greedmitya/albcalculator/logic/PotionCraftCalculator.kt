object PotionCraftCalculator {
    private const val EXTRA_COST_PERCENT = 0.08

    fun calculate(recipe: PotionRecipe): PotionCraftResult {
        val resourceSum = recipe.ingredients.sumOf { it.price }
        val baseWithPlacement = resourceSum * (1 + recipe.cityTaxPercent)

        val total = if (recipe.useFocus) {
            (baseWithPlacement * (1 + EXTRA_COST_PERCENT)) * 0.85
        } else {
            baseWithPlacement * (1 + EXTRA_COST_PERCENT)
        }

        return PotionCraftResult(
            totalResources = resourceSum,
            withPlacementFee = baseWithPlacement,
            finalCost = total
        )
    }
}