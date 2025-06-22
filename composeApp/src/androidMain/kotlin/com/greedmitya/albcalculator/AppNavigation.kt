package com.greedmitya.albcalculator

import androidx.compose.runtime.Composable
import com.greedmitya.albcalculator.ui.screens.craft.CraftScreen


@Composable
fun AppNavigation(
    craftViewModel: CraftViewModel
) {
    CraftScreen(viewModel = craftViewModel)
}




