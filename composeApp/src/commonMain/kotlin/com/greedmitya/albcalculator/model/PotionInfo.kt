data class PotionInfo(
    val displayName: String,
    val baseId: String,
    val availableTiers: List<String>,
    val outputQuantity: Int = 5,
) {
    fun getFullItemId(tier: String, enchantIndex: Int): String {
        return if (enchantIndex == 0) "${tier}_$baseId"
        else "${tier}_$baseId@$enchantIndex"
    }
}
