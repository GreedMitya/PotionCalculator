package com.greedmitya.albcalculator

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.greedmitya.albcalculator.storage.FavoritesStorage

@Composable
fun AndroidApp() {
    // 1) Скрываем системные бары
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isSystemBarsVisible = false
    }

    // 2) Берём ViewModel
    val context = LocalContext.current
    val viewModel = remember { CraftViewModel() }

// загрузи сохранённые избранные
    LaunchedEffect(Unit) {
        val saved = FavoritesStorage.loadFavorites(context)
        viewModel.loadFavoritesExternally(saved)
    }

// слушай и сохраняй при изменении
    LaunchedEffect(viewModel) {
        snapshotFlow { viewModel.currentFavorites() }
            .collect { newList ->
                FavoritesStorage.saveFavorites(context, newList)
            }
    }




    // 3) Запускаем навигацию, передавая ViewModel
    MaterialTheme {
        AppNavigation(viewModel)
    }
}
