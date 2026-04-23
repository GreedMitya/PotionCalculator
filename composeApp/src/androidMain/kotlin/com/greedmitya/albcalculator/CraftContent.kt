package com.greedmitya.albcalculator

import androidx.activity.compose.LocalActivity
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import kotlin.math.roundToInt
import com.greedmitya.albcalculator.components.ActionIconMenuButton
import com.greedmitya.albcalculator.components.ActionTextButton
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.components.CraftSubTabRow
import com.greedmitya.albcalculator.components.InputField
import com.greedmitya.albcalculator.components.ResultItem
import com.greedmitya.albcalculator.components.SelectorBlock
import com.greedmitya.albcalculator.components.SmallInputField
import com.greedmitya.albcalculator.components.ToggleOption
import com.greedmitya.albcalculator.components.showTimedSnackbar
import com.greedmitya.albcalculator.i18n.LocalGameNameProvider
import com.greedmitya.albcalculator.ui.components.IngredientItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import potioncalculator.composeapp.generated.resources.*


@Composable
fun CraftContent(
    viewModel: CraftViewModel,
    isReady: Boolean,
    isMarketReady: Boolean,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    scrollState: ScrollState
) {
    val isPremium = viewModel.isPremium
    val useFocus = viewModel.useFocus
    var craftSubTab by rememberSaveable { mutableIntStateOf(0) }
    val activity = LocalActivity.current
    val gameNameProvider = LocalGameNameProvider.current

    val copiedText = stringResource(Res.string.snackbar_copied)
    val fillText = stringResource(Res.string.snackbar_fill)
    val networkError = viewModel.networkError
    LaunchedEffect(networkError) {
        networkError?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearNetworkError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 30.dp, end = 30.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.app_title),
                color = AppColors.PrimaryGold,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(Res.string.craft_subtitle),
                color = AppColors.PrimaryGold,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            )
        }

        CraftSubTabRow(
            selectedIndex = craftSubTab,
            isPremiumUnlocked = viewModel.isAppPremiumUnlocked,
            onTabSelected = {
                craftSubTab = it
                if (it == 0) viewModel.useFocus = false
            },
        )

        Spacer(Modifier.height(16.dp))

        if (craftSubTab == 1 && !viewModel.isAppPremiumUnlocked) {
            PremiumUpgradeScreen(
                onBuyClick = { activity?.let { viewModel.purchasePremium(it) } },
                onRestoreClick = { viewModel.restorePurchases() },
                premiumPrice = viewModel.premiumPrice,
                isPurchasing = viewModel.isPurchasing,
                modifier = Modifier.weight(1f),
            )
            return
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
        ) {

        SelectorBlock(
            title = stringResource(Res.string.craft_selector_potion),
            options = viewModel.potions,
            selectedOption = viewModel.selectedPotion,
            onOptionSelected = { viewModel.onPotionSelected(it) },
            isError = viewModel.isPotionError,
            menuMaxHeight = 240.dp,
            modifier = Modifier.fillMaxWidth(),
            displayTransform = { gameNameProvider.getPotionDisplayName(it) },
        )

        Spacer(Modifier.height(12.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectorBlock(
                title = stringResource(Res.string.craft_selector_tier),
                options = viewModel.availableTiers,
                selectedOption = viewModel.selectedTier,
                onOptionSelected = { viewModel.selectedTier = it },
                isError = viewModel.isTierError,
                modifier = Modifier.weight(1f)
            )

            val enchantOptions = if (viewModel.selectedPotion != null) viewModel.availableEnchantments else emptyList()

            SelectorBlock(
                title = stringResource(Res.string.craft_selector_enchantment),
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
                title = stringResource(Res.string.craft_selector_city),
                options = viewModel.cities,
                selectedOption = viewModel.selectedCity,
                onOptionSelected = { viewModel.selectedCity = it },
                isError = viewModel.isCityError,
                modifier = Modifier.weight(1f)
            )

            InputField(
                title = stringResource(Res.string.craft_fee_label),
                value = viewModel.feePerNutritionInput,
                onValueChange = { viewModel.feePerNutritionInput = it },
                modifier = Modifier.weight(1f),
                isNumeric = true,
                isError = viewModel.isFeeError
            )
        }

        Spacer(Modifier.height(24.dp))

        if (craftSubTab == 0) {
            // Free Craft tab: only Premium toggle, no focus
            ToggleOption(
                label = stringResource(Res.string.craft_toggle_premium),
                checked = isPremium,
                onCheckedChange = { viewModel.isPremium = it },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            // Craft+ tab: Premium + Focus toggles + Batch quantity
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ToggleOption(
                    label = stringResource(Res.string.craft_toggle_premium),
                    checked = isPremium,
                    onCheckedChange = { viewModel.isPremium = it },
                    modifier = Modifier.weight(1f)
                )

                ToggleOption(
                    label = stringResource(Res.string.craft_toggle_focus),
                    checked = useFocus,
                    onCheckedChange = { viewModel.useFocus = it },
                    modifier = Modifier.weight(1f)
                )
            }

            if (useFocus) {
                Spacer(Modifier.height(12.dp))
                SmallInputField(
                    title = stringResource(Res.string.craft_focus_general),
                    hint = stringResource(Res.string.craft_focus_general_hint),
                    placeholder = "0 – 100",
                    value = viewModel.focusGeneralSpec,
                    onValueChange = { viewModel.focusGeneralSpec = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(8.dp))
                SmallInputField(
                    title = stringResource(Res.string.craft_focus_basic),
                    hint = stringResource(Res.string.craft_focus_basic_hint),
                    placeholder = "0 – 1500",
                    value = viewModel.focusBasic,
                    onValueChange = { viewModel.focusBasic = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(8.dp))
                SmallInputField(
                    title = stringResource(Res.string.craft_focus_mastery),
                    hint = stringResource(Res.string.craft_focus_mastery_hint),
                    placeholder = "0 – 100",
                    value = viewModel.focusMastery,
                    onValueChange = { viewModel.focusMastery = it },
                    modifier = Modifier.fillMaxWidth(),
                )

                val efficiencyInfo by remember {
                    derivedStateOf {
                        val g = viewModel.focusGeneralSpec.toDoubleOrNull() ?: 0.0
                        val b = viewModel.focusBasic.toDoubleOrNull() ?: 0.0
                        val m = viewModel.focusMastery.toDoubleOrNull() ?: 0.0
                        val pts = (g * 30.0 + b * 18.0 + m * 250.0).toLong()
                        val savePct = ((1.0 - 0.5.pow(pts / 10_000.0)) * 100).roundToInt()
                        if (pts > 0) Pair(pts, savePct) else null
                    }
                }
                efficiencyInfo?.let { (pts, savePct) ->
                    Spacer(Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = stringResource(Res.string.craft_focus_efficiency_pts, "%,d".format(pts)),
                            color = AppColors.LightBeige.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                        )
                        Text(
                            text = stringResource(Res.string.craft_focus_efficiency_save, savePct),
                            color = AppColors.LightBeige.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                SmallInputField(
                    title = stringResource(Res.string.craft_focus_available),
                    hint = stringResource(Res.string.craft_focus_available_hint),
                    value = viewModel.availableFocus,
                    onValueChange = { viewModel.availableFocus = it },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(Modifier.height(12.dp))
            InputField(
                title = stringResource(Res.string.craft_runs_label),
                value = viewModel.craftQuantity,
                onValueChange = { viewModel.craftQuantity = it },
                modifier = Modifier.fillMaxWidth(),
                isNumeric = true,
            )
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
                    craftQuantity = if (craftSubTab == 1) viewModel.craftQuantityInt else 1,
                    showTotalCost = craftSubTab == 1,
                    onCopy = {
                        coroutineScope.showTimedSnackbar(snackbarHostState, copiedText, 1200)
                    },
                )
                Spacer(Modifier.height(12.dp))
            }

            val result = viewModel.result
            if (viewModel.selectedPotion != null) {
                ResultItem(
                    potionDisplayName = run {
                        val potion = viewModel.selectedPotion ?: ""
                        if (potion == "Alcohol") {
                            gameNameProvider.getIngredientName("${viewModel.selectedTier ?: "T6"}_ALCOHOL")
                        } else {
                            gameNameProvider.getPotionDisplayName(potion)
                        }
                    },
                    potionEnglishName = viewModel.selectedPotion ?: "",
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
                    craftQuantity = if (craftSubTab == 1) viewModel.craftQuantityInt else 1,
                    totalProfit = result?.totalProfitFormatted ?: "",
                    onCopy = {
                        coroutineScope.showTimedSnackbar(snackbarHostState, copiedText, 1200)
                    },
                )

                val focusResult = result
                if (craftSubTab == 1 && useFocus && focusResult != null && focusResult.focusCostPerBatch > 0) {
                    Spacer(Modifier.height(8.dp))
                    val craftQty = if (craftSubTab == 1) viewModel.craftQuantityInt else 1
                    val outputQty = focusResult.outputQuantity
                    val returnPct = "%.1f".format(focusResult.effectiveReturnRate * 100)
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = stringResource(Res.string.focus_batches_info, focusResult.batchesWithFocus * outputQty, craftQty * outputQty),
                            color = AppColors.LightBeige.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                        )
                        Text(
                            text = stringResource(Res.string.focus_return_rate_info, returnPct),
                            color = AppColors.LightBeige.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                        )
                    }
                    if (focusResult.reducedFocusCostPerBatch != focusResult.focusCostPerBatch) {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = stringResource(Res.string.focus_cost_info, focusResult.reducedFocusCostPerBatch),
                            color = AppColors.LightBeige.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                        )
                    }
                }
            }

            Spacer(Modifier.height(40.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionTextButton(
                    text = stringResource(Res.string.button_calculate),
                    onClick = {
                        if (isReady) {
                            viewModel.calculateProfit()
                        } else {
                            viewModel.triggerValidationForCalculate()
                            coroutineScope.launch {
                                if (snackbarHostState.currentSnackbarData == null) {
                                    coroutineScope.showTimedSnackbar(snackbarHostState, fillText, 1200)
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
                    text = stringResource(Res.string.button_market),
                    onClick = {
                        if (isMarketReady) {
                            viewModel.fetchPricesForCurrentRecipe()
                        } else {
                            viewModel.triggerValidationForMarket()
                            coroutineScope.launch {
                                if (snackbarHostState.currentSnackbarData == null) {
                                    coroutineScope.showTimedSnackbar(snackbarHostState, fillText, 1200)
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
        } // end scrollable Column
    }
}