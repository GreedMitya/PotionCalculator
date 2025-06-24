data class PotionInfo(
    val displayName: String,
    val baseId: String,
    val availableTiers: List<String> // ← например, listOf("T4", "T5", "T6")
) {
    fun getFullItemId(tier: String, enchantIndex: Int): String {
        return if (enchantIndex == 0) "${tier}_$baseId"
        else "${tier}_$baseId@$enchantIndex"
    }
}
