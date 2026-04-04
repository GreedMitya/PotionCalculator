package com.greedmitya.albcalculator

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AndroidApp() {
    val viewModel = koinViewModel<CraftViewModel>()

    MaterialTheme {
        AppNavigation(viewModel)
    }
}
