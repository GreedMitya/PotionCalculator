data class Ingredient(
    val name: String,
    val quantity: Int,
    val price: Double? = null
)

data class PotionRecipe(
    val potionId: String,
    val displayName: String,
    val tier: String,
    val enchantment: Int,
    val city: String?,
    val useFocus: Boolean,
    val ingredients: List<Ingredient>
)
