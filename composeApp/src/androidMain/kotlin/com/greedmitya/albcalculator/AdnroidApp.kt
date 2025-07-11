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
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isSystemBarsVisible = false
    }

    val context = LocalContext.current
    val viewModel = remember { CraftViewModel() }

    LaunchedEffect(Unit) {
        val saved = FavoritesStorage.loadFavorites(context)
        viewModel.loadFavoritesExternally(saved)
    }

    LaunchedEffect(viewModel) {
        snapshotFlow { viewModel.currentFavorites() }
            .collect { newList ->
                FavoritesStorage.saveFavorites(context, newList)
            }
    }


    MaterialTheme {
        AppNavigation(viewModel)
    }
}
