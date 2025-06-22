package com.greedmitya.albcalculator

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AndroidApp() {
    // 1) Скрываем системные бары
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isSystemBarsVisible = false
    }

    // 2) Берём ViewModel
    val craftViewModel: CraftViewModel = viewModel()

    // 3) Запускаем навигацию, передавая ViewModel
    MaterialTheme {
        AppNavigation(craftViewModel)
    }
}
