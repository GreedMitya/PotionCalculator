data class PotionRecipe(
    val name: String,
    val ingredients: List<Ingredient>,
    val useFocus: Boolean,
    val cityTaxPercent: Double // Пример: 0.15 для 15%
)