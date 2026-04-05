package com.greedmitya.albcalculator

import android.app.Activity
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.assets.loadIngredientImageBitmapById
import com.greedmitya.albcalculator.components.ActionTextButton
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.components.SelectorBlock
import com.greedmitya.albcalculator.domain.ALL_CITIES
import com.greedmitya.albcalculator.domain.CITY_SHORT_NAMES
import com.greedmitya.albcalculator.domain.MarketPriceRow
import com.greedmitya.albcalculator.domain.PotionAdvisorResult
import com.greedmitya.albcalculator.ui.theme.EBGaramond
import com.greedmitya.albcalculator.util.formatSilver

@Composable
fun MarketsScreen(
    craftViewModel: CraftViewModel,
    marketsViewModel: MarketsViewModel,
    scrollState: ScrollState,
) {
    val activity = LocalContext.current as? Activity

    // Premium gate
    if (!craftViewModel.isAppPremiumUnlocked) {
        PremiumUpgradeScreen(
            onBuyClick = { activity?.let { craftViewModel.purchasePremium(it) } },
            onRestoreClick = { craftViewModel.restorePurchases() },
        )
        return
    }

    var subTab by rememberSaveable { mutableIntStateOf(0) }
    val subTabLabels = listOf("Herbs", "Components", "Potions", "Advisor")

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
            text = "Markets",
            color = AppColors.PrimaryGold,
            fontSize = 20.sp,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.SemiBold,
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

        when (subTab) {
            0 -> MarketPriceContent(
                state = marketsViewModel.herbsState,
                onRefresh = { marketsViewModel.loadHerbs(serverCode) },
                serverName = craftViewModel.selectedServer,
                highlightHighest = false,
            )
            1 -> MarketPriceContent(
                state = marketsViewModel.componentsState,
                onRefresh = { marketsViewModel.loadComponents(serverCode) },
                serverName = craftViewModel.selectedServer,
                highlightHighest = false,
            )
            2 -> {
                val potionItemIds = remember(craftViewModel.allPotions) {
                    craftViewModel.allPotions.flatMap { potion ->
                        potion.availableTiers.map { tier -> potion.getFullItemId(tier, 0) }
                    }
                }
                MarketPriceContent(
                    state = marketsViewModel.potionsState,
                    onRefresh = { marketsViewModel.loadPotions(potionItemIds, serverCode) },
                    serverName = craftViewModel.selectedServer,
                    highlightHighest = true,
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
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Server: $serverName",
            color = AppColors.Secondary_Beige,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f),
        )
        ActionTextButton(
            text = "Refresh",
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
                text = "Tap Refresh to load prices",
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
                    MarketItemCard(row = row, highlightHighest = highlightHighest)
                }
            }
        }
    }
}

@Composable
private fun MarketItemCard(row: MarketPriceRow, highlightHighest: Boolean) {
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
        // Header: icon + name + best price
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                bitmap = loadIngredientImageBitmapById(row.itemId),
                contentDescription = row.displayName,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(6.dp)),
            )
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = row.displayName,
                    color = AppColors.LightBeige,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = EBGaramond,
                )
                if (bestPrice > 0 && bestCity != null) {
                    val label = if (highlightHighest) "Best sell" else "Best buy"
                    val shortCity = CITY_SHORT_NAMES[bestCity] ?: bestCity
                    Text(
                        text = "$label: ${formatSilver(bestPrice.toDouble())} · $shortCity",
                        color = AppColors.PrimaryGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                } else {
                    Text(
                        text = "No data",
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

            // City prices — 3 per row
            val cityChunks = ALL_CITIES.chunked(3)
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
            title = "City",
            options = ALL_CITIES,
            selectedOption = selectedCity,
            onOptionSelected = { selectedCity = it },
            modifier = Modifier.weight(1f),
        )
        ActionTextButton(
            text = "Analyze",
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
                text = "Select a city and tap Analyze",
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

            if (output.topProfitable.isNotEmpty()) {
                Text(
                    text = "TOP ${output.topProfitable.size} — MOST PROFITABLE",
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
            }

            if (output.topForLeveling.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "TOP ${output.topForLeveling.size} — FOR LEVELING",
                    color = AppColors.Secondary_Beige,
                    fontFamily = EBGaramond,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    text = "Small loss, high craft XP",
                    color = AppColors.Gray300,
                    fontSize = 12.sp,
                )
                Spacer(Modifier.height(8.dp))
                output.topForLeveling.forEachIndexed { index, result ->
                    AdvisorResultRow(rank = index + 1, result = result)
                    Spacer(Modifier.height(6.dp))
                }
            }

            if (output.totalSkipped > 0) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "${output.totalSkipped} potions skipped — missing market data",
                    color = AppColors.Gray300,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
private fun AdvisorResultRow(rank: Int, result: PotionAdvisorResult) {
    val enchantSuffix = if (result.enchantment > 0) " (.${result.enchantment})" else ""
    val profitColor = if (result.profitSilver >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
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
                text = "${result.tier} ${result.potionDisplayName}$enchantSuffix",
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
