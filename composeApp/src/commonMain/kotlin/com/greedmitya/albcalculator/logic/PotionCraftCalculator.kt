import com.greedmitya.albcalculator.model.PotionCraftResult

object PotionCraftCalculator {

    fun calculate(
        ingredients: List<Ingredient>,
        feePerNutrition: Double,
        useFocus: Boolean,
        isPremium: Boolean,
        focusBasic: Double?,
        focusMastery: Double?,
        focusTotal: Double?,
        itemValue: Int,
        city: String?,
        sellPrice: Double?,
        outputQuantity: Int
    ): PotionCraftResult {
        // 1. Сегрегация ингредиентов
        val rareIngredients = ingredients.filter { it.name.contains("RARE", ignoreCase = true) }
        val regularIngredients = ingredients - rareIngredients

        // 2. Считаем по группам
        val rareCost = rareIngredients.sumOf { (it.price ?: 0.0) * it.quantity }
        val regularRawCost = regularIngredients.sumOf { (it.price ?: 0.0) * it.quantity }

        // 3. Возврат ресурсов только с обычных
        val returnRate = when (city) {
            "Brecilien" -> 0.248
            else -> 0.152
        }
        val regularAfterReturn = regularRawCost * (1 - returnRate)

        // 4. Общая стоимость всех ресурсов после возврата
        val totalCostAfterReturn = rareCost + regularAfterReturn

        // 5. Налог за крафт
        val craftingTax = feePerNutrition * itemValue * 0.001125
        val craftingTaxPerItem = craftingTax / outputQuantity

        // 6. Себестоимость одной банки
        val costPerItem = (totalCostAfterReturn / outputQuantity) + craftingTaxPerItem

        // 7. Расчёт прибыли
        val taxRate = if (isPremium) 0.065 else 0.105
        val netSell = (sellPrice ?: 0.0) * (1 - taxRate)

        val profitSilver = netSell - costPerItem
        println("💰 isPremium=$isPremium, taxRate=$taxRate, netSell=$netSell, costPerItem=$costPerItem, profitSilver=$profitSilver")

        return PotionCraftResult(
            totalResources = rareCost + regularRawCost,
            withPlacementFee = totalCostAfterReturn,
            finalCost = costPerItem,
            estimatedSellPrice = sellPrice,
            profitSilver = profitSilver
        )
    }
    }

