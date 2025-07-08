package com.greedmitya.albcalculator

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.components.SelectorBlock
import com.greedmitya.albcalculator.storage.FavoritesStorage
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(viewModel: CraftViewModel, scrollState: ScrollState) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val storedServer = FavoritesStorage.loadSelectedServer(context)
        viewModel.updateServer(storedServer)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BackgroundDark)
            .padding(horizontal = 20.dp, vertical = 60.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Potion Crafting",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold
            )
            Text(
                text = "Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold
            )
        }

        Spacer(Modifier.height(24.dp))

        SelectorBlock(
            title = "Server Region for API prices",
            options = viewModel.serverDisplayNames.values.toList(),
            selectedOption = viewModel.selectedServer,
            onOptionSelected = {
                viewModel.updateServer(it)
                coroutineScope.launch {
                    FavoritesStorage.saveSelectedServer(context, it)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

