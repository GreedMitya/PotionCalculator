package com.greedmitya.albcalculator

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.components.ActionIconMenuButton
import com.greedmitya.albcalculator.components.ActionTextButton
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.components.InputField
import com.greedmitya.albcalculator.components.ResultItem
import com.greedmitya.albcalculator.components.SelectorBlock
import com.greedmitya.albcalculator.components.SmallInputField
import com.greedmitya.albcalculator.components.ToggleOption
import com.greedmitya.albcalculator.components.showTimedSnackbar
import com.greedmitya.albcalculator.ui.components.IngredientItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CraftContent(
    viewModel: CraftViewModel,
    isReady: Boolean,
    isMarketReady: Boolean,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    scrollState: ScrollState
) {
    val isPremium by viewModel::isPremium
    val useFocus by viewModel::useFocus

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 40.dp, start = 30.dp, end = 30.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Potion Crafting",
                color = AppColors.PrimaryGold,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Profit Calculator",
                color = AppColors.PrimaryGold,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            )
        }
        SelectorBlock(
            title = "Potion",
            options = viewModel.potions,
            selectedOption = viewModel.selectedPotion,
            onOptionSelected = { viewModel.onPotionSelected(it) },
            isError = viewModel.isPotionError,
            menuMaxHeight = 240.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectorBlock(
                title = "Tier",
                options = viewModel.availableTiers,
                selectedOption = viewModel.selectedTier,
                onOptionSelected = { viewModel.selectedTier = it },
                isError = viewModel.isTierError,
                modifier = Modifier.weight(1f)
            )

            val enchantOptions = if (viewModel.selectedPotion != null) viewModel.enchantments else emptyList()

            SelectorBlock(
                title = "Enchantment",
                options = enchantOptions,
                selectedOption = viewModel.selectedEnchantment,
                onOptionSelected = { viewModel.selectedEnchantment = it },
                isError = viewModel.isEnchantmentError,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectorBlock(
                title = "City",
                options = viewModel.cities,
                selectedOption = viewModel.selectedCity,
                onOptionSelected = { viewModel.selectedCity = it },
                isError = viewModel.isCityError,
                modifier = Modifier.weight(1f)
            )

            InputField(
                title = "Fee for 100 nutrition",
                value = viewModel.feePerNutritionInput,
                onValueChange = { viewModel.feePerNutritionInput = it },
                modifier = Modifier.weight(1f),
                isNumeric = true,
                isError = viewModel.isFeeError
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ToggleOption(
                label = "Premium",
                checked = isPremium,
                onCheckedChange = { viewModel.isPremium = it },
                modifier = Modifier.weight(1f)
            )

            ToggleOption(
                label = "Use focus",
                checked = useFocus,
                onCheckedChange = { viewModel.useFocus = it },
                modifier = Modifier.weight(1f)
            )
        }

        if (useFocus) {
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallInputField("Basic", viewModel.focusBasic, { viewModel.focusBasic = it }, Modifier.weight(1f))
                SmallInputField(
                    "Mastery",
                    viewModel.focusMastery,
                    { viewModel.focusMastery = it },
                    Modifier.weight(1f)
                )
                SmallInputField("Total", viewModel.focusTotal, { viewModel.focusTotal = it }, Modifier.weight(1f))
            }
        }

        Spacer(Modifier.height(24.dp))
        val ingredients = viewModel.getRecipeForSelected()

        Column {
            ingredients.forEach { ingredient ->
                IngredientItem(
                    isError = viewModel.isIngredientError(ingredient.name),
                    ingredient = ingredient.copy(
                        price = viewModel.ingredientPrices[ingredient.name]?.toDoubleOrNull()
                    ),
                    onPriceChange = { newPrice ->
                        viewModel.ingredientPrices[ingredient.name] = newPrice
                    },
                    onCopy = {
                        coroutineScope.showTimedSnackbar(snackbarHostState, "Copied!", 1200)
                    }
                )
                Spacer(Modifier.height(12.dp))
            }

            val result by remember { derivedStateOf { viewModel.result } }
            if (viewModel.selectedPotion != null) {
                ResultItem(
                    potionDisplayName = viewModel.selectedPotion ?: "",
                    tier = viewModel.selectedTier ?: "T4",
                    enchantment = viewModel.enchantments.indexOf(viewModel.selectedEnchantment ?: "Normal (.0)"),
                    pricePerItem = viewModel.potionSellPrice,
                    onPriceChange = { viewModel.potionSellPrice = it },
                    profitSilver = result?.profitSilverFormatted ?: "-/-",
                    profitPercent = result?.profitPercentFormatted ?: "-/-",
                    quantity = viewModel.outputQuantity,
                    isBlinking = viewModel.isBlinkingResult,
                    shimmerColor = viewModel.shimmerColor.value,
                    isError = viewModel.isSellPriceError,
                    onCopy = {
                        coroutineScope.showTimedSnackbar(snackbarHostState, "Copied!", 1200)
                    }
                )
            }

            Spacer(Modifier.height(40.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionTextButton(
                    text = "Calculate",
                    onClick = {
                        if (isReady) {
                            viewModel.calculateProfit()
                        } else {
                            viewModel.triggerValidationForCalculate()
                            coroutineScope.launch {
                                if (snackbarHostState.currentSnackbarData == null) {
                                    coroutineScope.showTimedSnackbar(snackbarHostState, "Fill!", 1200)
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = true,
                    backgroundColor = if (isReady) AppColors.PrimaryGold else AppColors.Gray200,
                    textColor = if (isReady) AppColors.PanelBrown else AppColors.Gray300,
                    borderColor = if (isReady) AppColors.PrimaryGold else AppColors.Gray200
                )

                ActionTextButton(
                    text = "Market",
                    onClick = {
                        if (isMarketReady) {
                            viewModel.fetchPricesForCurrentRecipe()
                        } else {
                            viewModel.triggerValidationForMarket()
                            coroutineScope.launch {
                                if (snackbarHostState.currentSnackbarData == null) {
                                    coroutineScope.showTimedSnackbar(snackbarHostState, "Fill!", 1200)
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = true,
                    textColor = if (isMarketReady) AppColors.PanelBrown else AppColors.Gray300,
                    backgroundColor = if (isMarketReady) AppColors.Secondary_Beige else AppColors.Gray200,
                    borderColor = if (isMarketReady) AppColors.PrimaryGold else AppColors.Gray200
                )

                ActionIconMenuButton(
                    iconResId = if (isMarketReady) R.drawable.button_dots_true else R.drawable.button_dots_false,
                    enabled = true,
                    onResetClick = { viewModel.resetPrices() },
                    onSaveClick = { viewModel.saveToFavorites() }
                )
            }
        }
    }
}