package com.greedmitya.albcalculator

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.assets.getLocalizedPotionName
import com.greedmitya.albcalculator.assets.loadIngredientImageBitmapById
import com.greedmitya.albcalculator.components.SelectorBlock
import com.greedmitya.albcalculator.assets.loadPotionImageBitmapFromDisplayName
import com.greedmitya.albcalculator.components.ActionTextButton
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.domain.ALL_CITIES
import com.greedmitya.albcalculator.domain.CITY_SHORT_NAMES
import com.greedmitya.albcalculator.domain.MarketPriceRow
import com.greedmitya.albcalculator.domain.PotionAdvisorResult
import com.greedmitya.albcalculator.model.PotionInfo
import com.greedmitya.albcalculator.ui.theme.EBGaramond
import com.greedmitya.albcalculator.util.formatSilver
import org.jetbrains.compose.resources.stringResource
import potioncalculator.composeapp.generated.resources.Res
import potioncalculator.composeapp.generated.resources.craft_selector_city
import potioncalculator.composeapp.generated.resources.markets_advisor_leveling_note
import potioncalculator.composeapp.generated.resources.markets_advisor_potions_skipped
import potioncalculator.composeapp.generated.resources.markets_advisor_show_less
import potioncalculator.composeapp.generated.resources.markets_advisor_show_more
import potioncalculator.composeapp.generated.resources.markets_advisor_top_leveling
import potioncalculator.composeapp.generated.resources.markets_advisor_top_profitable
import potioncalculator.composeapp.generated.resources.markets_button_analyze
import potioncalculator.composeapp.generated.resources.markets_button_refresh
import potioncalculator.composeapp.generated.resources.markets_empty_analyze
import potioncalculator.composeapp.generated.resources.markets_empty_refresh
import potioncalculator.composeapp.generated.resources.markets_filter_enchant
import potioncalculator.composeapp.generated.resources.markets_filter_tier
import potioncalculator.composeapp.generated.resources.markets_label_best_buy
import potioncalculator.composeapp.generated.resources.markets_label_best_sell
import potioncalculator.composeapp.generated.resources.markets_label_no_data
import potioncalculator.composeapp.generated.resources.markets_label_server
import potioncalculator.composeapp.generated.resources.markets_subtab_advisor
import potioncalculator.composeapp.generated.resources.markets_subtab_components
import potioncalculator.composeapp.generated.resources.markets_subtab_herbs
import potioncalculator.composeapp.generated.resources.markets_subtab_potions
import potioncalculator.composeapp.generated.resources.markets_subtitle
import potioncalculator.composeapp.generated.resources.title_potion_crafting

@Composable
fun MarketsScreen(
    craftViewModel: CraftViewModel,
    marketsViewModel: MarketsViewModel,
    scrollState: ScrollState,
) {
    val activity = LocalContext.current as? Activity

    // Premium gate
    if (!craftViewModel.isAppPremiumUnlocked) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.BackgroundDark)
                .padding(horizontal = 20.dp, vertical = 60.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.title_potion_crafting),
                    color = AppColors.PrimaryGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                )

                Text(
                    text = stringResource(Res.string.markets_subtitle),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    color = AppColors.PrimaryGold
                )
            }
            PremiumUpgradeScreen(
                onBuyClick = { activity?.let { craftViewModel.purchasePremium(it) } },
                onRestoreClick = { craftViewModel.restorePurchases() },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 60.dp)
            )
        }
        return
    }

    var subTab by rememberSaveable { mutableIntStateOf(0) }
    val subTabLabels = listOf(
        stringResource(Res.string.markets_subtab_herbs),
        stringResource(Res.string.markets_subtab_components),
        stringResource(Res.string.markets_subtab_potions),
        stringResource(Res.string.markets_subtab_advisor),
    )

    val serverCode = craftViewModel.serverDisplayNames.entries
        .firstOrNull { it.value == craftViewModel.selectedServer }?.key ?: "europe"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(Res.string.title_potion_crafting),
            color = AppColors.PrimaryGold,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif,
        )

        Text(
            text = stringResource(Res.string.markets_subtitle),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Serif,
            color = AppColors.PrimaryGold
        )

        Spacer(Modifier.height(16.dp))

        // Sub-tab row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(AppColors.Gray500)
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            subTabLabels.forEachIndexed { index, label ->
                val isSelected = subTab == index
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) AppColors.PrimaryGold else AppColors.Gray500)
                        .clickable { subTab = index },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) AppColors.BackgroundDark else AppColors.Gray300,
                        fontFamily = EBGaramond,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Persistent filter state (survives sub-tab switches)
        var componentTierFilter by rememberSaveable { mutableStateOf("All") }
        var potionTierFilter by rememberSaveable { mutableStateOf("All") }
        var potionEnchantFilter by rememberSaveable { mutableStateOf("All") }

        when (subTab) {
            0 -> MarketPriceContent(
                state = marketsViewModel.herbsState,
                onRefresh = { marketsViewModel.loadHerbs(serverCode) },
                serverName = craftViewModel.selectedServer,
                highlightHighest = false,
            )
            1 -> {
                // Tier filter — full width for a clean professional look
                val componentTiers = listOf("All", "T1", "T3", "T4", "T5", "T6", "T7", "T8")
                SelectorBlock(
                    title = stringResource(Res.string.markets_filter_tier),
                    options = componentTiers,
                    selectedOption = componentTierFilter,
                    onOptionSelected = { componentTierFilter = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(12.dp))
                MarketPriceContent(
                    state = filterMarketRowsByTier(marketsViewModel.componentsState, componentTierFilter),
                    onRefresh = { marketsViewModel.loadComponents(serverCode) },
                    serverName = craftViewModel.selectedServer,
                    highlightHighest = false,
                )
            }
            2 -> {
                // Include all enchant levels (0–3) so the enchant filter has data to work with
                val potionItemIds = remember(craftViewModel.allPotions) {
                    craftViewModel.allPotions.flatMap { potion ->
                        val enchantRange = if (potion.hasEnchants) listOf(0, 1, 2, 3) else listOf(0)
                        potion.availableTiers.flatMap { tier ->
                            enchantRange.map { enchant -> potion.getFullItemId(tier, enchant) }
                        }
                    }
                }
                // Tier + Enchant filters
                val potionTiers = listOf("All", "T2", "T3", "T4", "T5", "T6", "T7", "T8")
                val potionEnchants = listOf("All", ".0", ".1", ".2", ".3")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    SelectorBlock(
                        title = stringResource(Res.string.markets_filter_tier),
                        options = potionTiers,
                        selectedOption = potionTierFilter,
                        onOptionSelected = { potionTierFilter = it },
                        modifier = Modifier.weight(1f),
                    )
                    SelectorBlock(
                        title = stringResource(Res.string.markets_filter_enchant),
                        options = potionEnchants,
                        selectedOption = potionEnchantFilter,
                        onOptionSelected = { potionEnchantFilter = it },
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(Modifier.height(12.dp))
                MarketPriceContent(
                    state = filterMarketPotions(
                        state = marketsViewModel.potionsState,
                        tier = potionTierFilter,
                        enchant = potionEnchantFilter,
                    ),
                    onRefresh = { marketsViewModel.loadPotions(potionItemIds, serverCode) },
                    serverName = craftViewModel.selectedServer,
                    highlightHighest = true,
                    allPotions = craftViewModel.allPotions,
                )
            }
            3 -> AdvisorContent(
                craftViewModel = craftViewModel,
                marketsViewModel = marketsViewModel,
                serverCode = serverCode,
            )
        }
    }
}

@Composable
private fun MarketPriceContent(
    state: MarketsUiState,
    onRefresh: () -> Unit,
    serverName: String,
    highlightHighest: Boolean,
    allPotions: List<PotionInfo> = emptyList(),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(Res.string.markets_label_server, serverName),
            color = AppColors.Secondary_Beige,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f),
        )
        ActionTextButton(
            text = stringResource(Res.string.markets_button_refresh),
            onClick = onRefresh,
            backgroundColor = AppColors.PrimaryGold,
            textColor = AppColors.BackgroundDark,
            borderColor = AppColors.PrimaryGold,
        )
    }

    Spacer(Modifier.height(12.dp))

    when (state) {
        is MarketsUiState.Idle -> {
            Text(
                text = stringResource(Res.string.markets_empty_refresh),
                color = AppColors.Gray300,
                fontSize = 14.sp,
            )
        }
        is MarketsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = AppColors.PrimaryGold)
            }
        }
        is MarketsUiState.Error -> {
            Text(
                text = "Error: ${state.message}",
                color = Color(0xFFF44336),
                fontSize = 14.sp,
            )
        }
        is MarketsUiState.Success -> {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                state.rows.forEach { row ->
                    val potionInfo = if (allPotions.isNotEmpty()) resolvePotionInfo(row.itemId, allPotions) else null
                    MarketItemCard(
                        row = row,
                        highlightHighest = highlightHighest,
                        potionInfo = potionInfo,
                    )
                }
            }
        }
    }
}

/** Parsed potion identity extracted from a raw item ID like "T4_POTION_HEAL@1". */
private data class PotionRenderInfo(val baseId: String, val displayName: String, val tier: String, val enchant: Int)

/** Returns non-null only if [itemId] matches a known potion base ID. */
private fun resolvePotionInfo(itemId: String, allPotions: List<PotionInfo>): PotionRenderInfo? {
    val atIndex = itemId.indexOf('@')
    val enchant = if (atIndex >= 0) itemId.substring(atIndex + 1).toIntOrNull() ?: 0 else 0
    val withoutEnchant = if (atIndex >= 0) itemId.substring(0, atIndex) else itemId
    val underscoreIndex = withoutEnchant.indexOf('_')
    if (underscoreIndex < 0) return null
    val tier = withoutEnchant.substring(0, underscoreIndex)
    val baseId = withoutEnchant.substring(underscoreIndex + 1)
    val potion = allPotions.find { it.baseId == baseId } ?: return null
    return PotionRenderInfo(baseId = potion.baseId, displayName = potion.displayName, tier = tier, enchant = enchant)
}

@Composable
private fun MarketItemCard(
    row: MarketPriceRow,
    highlightHighest: Boolean,
    potionInfo: PotionRenderInfo? = null,
) {
    val validPrices = row.pricesByCity.filter { it.value > 0 }
    val bestPrice = if (highlightHighest) {
        validPrices.values.maxOrNull() ?: 0
    } else {
        validPrices.values.minOrNull() ?: 0
    }
    val bestCity = if (bestPrice > 0) {
        if (highlightHighest) {
            validPrices.maxByOrNull { it.value }?.key
        } else {
            validPrices.minByOrNull { it.value }?.key
        }
    } else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Gray500, RoundedCornerShape(8.dp))
            .padding(12.dp),
    ) {
        // Resolve display name: use parsed potion name (localized) or raw row name
        val localizedPotionName = if (potionInfo != null) getLocalizedPotionName(potionInfo.baseId, potionInfo.displayName) else null
        val cardDisplayName = if (potionInfo != null && localizedPotionName != null) {
            "${potionInfo.tier} $localizedPotionName" +
                if (potionInfo.enchant > 0) " (.${potionInfo.enchant})" else ""
        } else {
            row.displayName
        }

        // Header: icon + name + best price
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                bitmap = if (potionInfo != null) {
                    loadPotionImageBitmapFromDisplayName(
                        displayName = potionInfo.displayName,
                        tier = potionInfo.tier,
                        enchant = potionInfo.enchant,
                    )
                } else {
                    loadIngredientImageBitmapById(row.itemId)
                },
                contentDescription = cardDisplayName,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(6.dp)),
            )
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cardDisplayName,
                    color = AppColors.LightBeige,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = EBGaramond,
                )
                if (bestPrice > 0 && bestCity != null) {
                    val label = if (highlightHighest) stringResource(Res.string.markets_label_best_sell) else stringResource(Res.string.markets_label_best_buy)
                    val shortCity = CITY_SHORT_NAMES[bestCity] ?: bestCity
                    Text(
                        text = "$label: ${formatSilver(bestPrice.toDouble())} · $shortCity",
                        color = AppColors.PrimaryGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                } else {
                    Text(
                        text = stringResource(Res.string.markets_label_no_data),
                        color = AppColors.Gray300,
                        fontSize = 12.sp,
                    )
                }
            }
        }

        if (validPrices.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(color = AppColors.Gray400, thickness = 1.dp)
            Spacer(Modifier.height(8.dp))

            // City prices — only cities with actual prices, 3 per row
            val citiesWithPrices = ALL_CITIES.filter { (row.pricesByCity[it] ?: 0) > 0 }
            val cityChunks = citiesWithPrices.chunked(3)
            cityChunks.forEach { rowCities ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowCities.forEach { city ->
                        val price = row.pricesByCity[city] ?: 0
                        val isHighlighted = price > 0 && price == bestPrice
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = CITY_SHORT_NAMES[city] ?: city,
                                color = if (isHighlighted) AppColors.PrimaryGold else AppColors.Gray300,
                                fontSize = 10.sp,
                                fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
                            )
                            Text(
                                text = if (price > 0) formatSilver(price.toDouble()) else "—",
                                color = if (isHighlighted) AppColors.PrimaryGold else AppColors.LightBeige,
                                fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp,
                            )
                        }
                    }
                    // Pad remaining slots in the last row
                    repeat(3 - rowCities.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun AdvisorContent(
    craftViewModel: CraftViewModel,
    marketsViewModel: MarketsViewModel,
    serverCode: String,
) {
    var selectedCity by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        SelectorBlock(
            title = stringResource(Res.string.craft_selector_city),
            options = ALL_CITIES,
            selectedOption = selectedCity,
            onOptionSelected = { selectedCity = it },
            modifier = Modifier.weight(1f),
        )
        ActionTextButton(
            text = stringResource(Res.string.markets_button_analyze),
            onClick = {
                selectedCity?.let { city ->
                    marketsViewModel.analyzeAdvisor(
                        allPotions = craftViewModel.allPotions,
                        city = city,
                        serverCode = serverCode,
                        isPremium = craftViewModel.isPremium,
                    )
                }
            },
            enabled = selectedCity != null,
            backgroundColor = if (selectedCity != null) AppColors.PrimaryGold else AppColors.Gray200,
            textColor = if (selectedCity != null) AppColors.BackgroundDark else AppColors.Gray300,
            borderColor = if (selectedCity != null) AppColors.PrimaryGold else AppColors.Gray200,
        )
    }

    Spacer(Modifier.height(16.dp))

    when (val state = marketsViewModel.advisorState) {
        is AdvisorUiState.Idle -> {
            Text(
                text = stringResource(Res.string.markets_empty_analyze),
                color = AppColors.Gray300,
                fontSize = 14.sp,
            )
        }
        is AdvisorUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = AppColors.PrimaryGold)
            }
        }
        is AdvisorUiState.Error -> {
            Text(
                text = "Error: ${state.message}",
                color = Color(0xFFF44336),
                fontSize = 14.sp,
            )
        }
        is AdvisorUiState.Success -> {
            val output = state.output
            var showAllProfitable by remember { mutableStateOf(false) }
            var showAllLeveling by remember { mutableStateOf(false) }

            if (output.topProfitable.isNotEmpty()) {
                Text(
                    text = stringResource(Res.string.markets_advisor_top_profitable, output.topProfitable.size),
                    color = AppColors.PrimaryGold,
                    fontFamily = EBGaramond,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Spacer(Modifier.height(8.dp))
                output.topProfitable.forEachIndexed { index, result ->
                    AdvisorResultRow(rank = index + 1, result = result)
                    Spacer(Modifier.height(6.dp))
                }

                // Show more / Show less toggle — only if there are extra results
                val extraCount = output.allProfitable.size - output.topProfitable.size
                if (extraCount > 0) {
                    AnimatedVisibility(
                        visible = showAllProfitable,
                        enter = expandVertically(),
                        exit = shrinkVertically(),
                    ) {
                        Column {
                            output.allProfitable.drop(output.topProfitable.size).forEachIndexed { index, result ->
                                AdvisorResultRow(rank = output.topProfitable.size + index + 1, result = result)
                                Spacer(Modifier.height(6.dp))
                            }
                        }
                    }

                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = if (showAllProfitable) stringResource(Res.string.markets_advisor_show_less) else stringResource(Res.string.markets_advisor_show_more, extraCount),
                        color = AppColors.PrimaryGold,
                        fontFamily = EBGaramond,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { showAllProfitable = !showAllProfitable }
                            .padding(vertical = 4.dp),
                    )
                }
            }

            if (output.topForLeveling.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(Res.string.markets_advisor_top_leveling, output.topForLeveling.size),
                    color = AppColors.Secondary_Beige,
                    fontFamily = EBGaramond,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    text = stringResource(Res.string.markets_advisor_leveling_note),
                    color = AppColors.Gray300,
                    fontSize = 12.sp,
                )
                Spacer(Modifier.height(8.dp))
                output.topForLeveling.forEachIndexed { index, result ->
                    AdvisorResultRow(rank = index + 1, result = result)
                    Spacer(Modifier.height(6.dp))
                }

                // Show more / Show less toggle for leveling
                val extraLevelingCount = output.allForLeveling.size - output.topForLeveling.size
                if (extraLevelingCount > 0) {
                    AnimatedVisibility(
                        visible = showAllLeveling,
                        enter = expandVertically(),
                        exit = shrinkVertically(),
                    ) {
                        Column {
                            output.allForLeveling.drop(output.topForLeveling.size).forEachIndexed { index, result ->
                                AdvisorResultRow(rank = output.topForLeveling.size + index + 1, result = result)
                                Spacer(Modifier.height(6.dp))
                            }
                        }
                    }

                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = if (showAllLeveling) stringResource(Res.string.markets_advisor_show_less) else stringResource(Res.string.markets_advisor_show_more, extraLevelingCount),
                        color = AppColors.Secondary_Beige,
                        fontFamily = EBGaramond,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { showAllLeveling = !showAllLeveling }
                            .padding(vertical = 4.dp),
                    )
                }
            }

            if (output.totalSkipped > 0) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(Res.string.markets_advisor_potions_skipped, output.totalSkipped),
                    color = AppColors.Gray300,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

// ── Market filter helpers ────────────────────────────────────────────────────

/**
 * Returns state with rows filtered to only the given tier prefix (e.g. "T4").
 * Passes through Loading/Error/Idle unchanged. "All" means no filter.
 */
private fun filterMarketRowsByTier(state: MarketsUiState, tier: String): MarketsUiState {
    if (tier == "All" || state !is MarketsUiState.Success) return state
    val prefix = "${tier}_"
    return MarketsUiState.Success(state.rows.filter { it.itemId.startsWith(prefix) })
}

/**
 * Returns potion state filtered by tier and/or enchant level.
 * "All" for either param means no filter on that dimension.
 */
private fun filterMarketPotions(
    state: MarketsUiState,
    tier: String,
    enchant: String,
): MarketsUiState {
    if (state !is MarketsUiState.Success) return state
    val tierPrefix = if (tier == "All") null else "${tier}_"
    val enchantIndex: Int? = when (enchant) {
        ".0" -> 0
        ".1" -> 1
        ".2" -> 2
        ".3" -> 3
        else -> null
    }
    return MarketsUiState.Success(
        state.rows.filter { row ->
            val tierOk = tierPrefix == null || row.itemId.startsWith(tierPrefix)
            val rowEnchant = row.itemId.indexOf('@').let { idx ->
                if (idx >= 0) row.itemId.substring(idx + 1).toIntOrNull() ?: 0 else 0
            }
            val enchantOk = enchantIndex == null || rowEnchant == enchantIndex
            tierOk && enchantOk
        }
    )
}

// ── Advisor result row ───────────────────────────────────────────────────────

@Composable
private fun AdvisorResultRow(rank: Int, result: PotionAdvisorResult) {
    val localizedName = getLocalizedPotionName(result.potionBaseId, result.potionDisplayName)
    val enchantSuffix = if (result.enchantment > 0) " (.${result.enchantment})" else ""
    val profitColor = if (result.profitSilver > 0) Color(0xFF4CAF50) else if(result.profitSilver < 0) Color(0xFFF44336)
    else AppColors.LightBeige
    val sign = if (result.profitSilver >= 0) "+" else ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Gray500, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$rank.",
            color = AppColors.PrimaryGold,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(24.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${result.tier} $localizedName$enchantSuffix",
                color = AppColors.LightBeige,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$sign${formatSilver(result.profitSilver)}",
                color = profitColor,
                fontFamily = EBGaramond,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
            Text(
                text = "(${String.format("%.1f", result.profitPercent)}%)",
                color = profitColor,
                fontSize = 11.sp,
            )
        }
    }
}
