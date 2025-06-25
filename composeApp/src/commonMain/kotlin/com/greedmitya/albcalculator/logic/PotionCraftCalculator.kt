import com.greedmitya.albcalculator.model.PotionCraftResult

object PotionCraftCalculator {

    fun calculate(
        ingredients: List<Ingredient>,
        feePerNutrition: Double,
        useFocus: Boolean,
        isPremium: Boolean,
        focusBasic: Double?,
        focusMastery: Double?,
        focusTotal: Double?
    ): PotionCraftResult {
        // 1. Сумма ресурсов
        val resourceSum = ingredients.sumOf {
            (it.price ?: 0.0) * it.quantity
        }

        // 2. Стоимость питания
        val fee = feePerNutrition

        // 3. Коэффициент фокуса (если включён)
        val focusReduction = if (useFocus && focusBasic != null && focusMastery != null && focusTotal != null) {
            val totalReduction = (focusBasic + focusMastery) / focusTotal
            totalReduction.coerceIn(0.0, 1.0)
        } else 0.0

        // 4. Стоимость с учётом фокуса
        val afterFocusCost = resourceSum * (1 - focusReduction)

        // 5. Применим премиум-бонус (например, 15% экономии)
        val finalCost = if (isPremium) afterFocusCost * 0.85 else afterFocusCost

        // 6. Учёт стоимости питания
        val total = finalCost + fee

        return PotionCraftResult(
            totalResources = resourceSum,
            withPlacementFee = finalCost,
            finalCost = total
        )
    }
}