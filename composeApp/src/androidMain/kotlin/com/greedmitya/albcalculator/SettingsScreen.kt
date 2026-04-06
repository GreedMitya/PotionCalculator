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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.components.SelectorBlock
import org.jetbrains.compose.resources.stringResource
import potioncalculator.composeapp.generated.resources.Res
import potioncalculator.composeapp.generated.resources.settings_selector_server_region
import potioncalculator.composeapp.generated.resources.settings_subtitle
import potioncalculator.composeapp.generated.resources.title_potion_crafting

@Composable
fun SettingsScreen(viewModel: CraftViewModel, scrollState: ScrollState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BackgroundDark)
            .padding(horizontal = 20.dp, vertical = 60.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.title_potion_crafting),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold
            )
            Text(
                text = stringResource(Res.string.settings_subtitle),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold
            )
        }

        Spacer(Modifier.height(24.dp))

        SelectorBlock(
            title = stringResource(Res.string.settings_selector_server_region),
            options = viewModel.serverDisplayNames.values.toList(),
            selectedOption = viewModel.selectedServer,
            onOptionSelected = { viewModel.updateServer(it) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

