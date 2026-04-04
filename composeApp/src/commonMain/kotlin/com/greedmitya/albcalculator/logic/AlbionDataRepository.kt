package com.greedmitya.albcalculator.logic

/**
 * Заглушка (Roadmap) для интеграции с Albion Data Project.
 * Будет использоваться для премиум функции Global Market Monitor.
 */
interface AlbionDataRepository {

    /**
     * Получение средних цен по всем городам для выбранного массива предметов.
     * Будет использоваться в PremiumManager.isPremium == true.
     */
    suspend fun getGlobalPrices(itemIds: List<String>): Map<String, GlobalPriceInfo>
    
    // В будущем тут будет использоваться httpClient на Ktor (который мы обновили до 3.0.0)
}

data class GlobalPriceInfo(
    val itemId: String,
    val minSellPriceLocation: String,
    val minSellPrice: Int,
    val maxBuyPriceLocation: String,
    val maxBuyPrice: Int
)
