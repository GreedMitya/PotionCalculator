package com.greedmitya.albcalculator.ui.screens.craft

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.CraftContent
import com.greedmitya.albcalculator.CraftViewModel
import com.greedmitya.albcalculator.FavoritesScreen
import com.greedmitya.albcalculator.HowToUseScreen
import com.greedmitya.albcalculator.R
import com.greedmitya.albcalculator.SettingsScreen
import com.greedmitya.albcalculator.components.*
import com.greedmitya.albcalculator.model.BottomNavItemData

@Composable
fun CraftScreen(viewModel: CraftViewModel) {
    val isPremium by viewModel::isPremium
    val useFocus by viewModel::useFocus
    val isReady by remember { derivedStateOf { viewModel.isReadyToCalculate } }
    val isMarketReady by remember { derivedStateOf { viewModel.isReadyForMarket } }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(0) }
    val scrollState = remember(selectedTab) { ScrollState(0) }

    val navItems = listOf(
        BottomNavItemData(
            label = "Craft",
            iconResId = R.drawable.ic_craft,
            selectedIconResId = R.drawable.ic_craft_active
        ),
        BottomNavItemData(
            label = "Favorites",
            iconResId = R.drawable.ic_favorites,
            selectedIconResId = R.drawable.ic_favorites_active
        ),
        BottomNavItemData(
            label = "How to use",
            iconResId = R.drawable.ic_how_to_use,
            selectedIconResId = R.drawable.ic_how_to_active
        ),
        BottomNavItemData(
            label = "Settings",
            iconResId = R.drawable.ic_settings,
            selectedIconResId = R.drawable.ic_settings_active
        )
    )

    Scaffold(
        containerColor = AppColors.BackgroundDark,
        snackbarHost = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { data ->
                        Snackbar(
                            containerColor = AppColors.Gray500,
                            contentColor = AppColors.White,
                            shape = RoundedCornerShape(26.dp),
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .wrapContentWidth()
                                .defaultMinSize(minWidth = 50.dp)
                                .widthIn(max = 120.dp)
                        ) {
                            Text(
                                text = data.visuals.message,
                                fontSize = 14.sp,
                                lineHeight = 18.sp,
                                maxLines = 2,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            Surface(
                color = AppColors.PanelBrown,
                tonalElevation = 4.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                BottomNavigationBar(
                    items = navItems,
                    selectedIndex = selectedTab,
                    onSelect = { selectedTab = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> CraftContent(
                    viewModel = viewModel,
                    isReady = isReady,
                    isMarketReady = isMarketReady,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                    scrollState = scrollState
                )
                1 -> FavoritesScreen(
                    viewModel = viewModel,
                    onNavigateToCraft = { selectedTab = 0 },
                    scrollState = scrollState
                )
                2 -> HowToUseScreen(scrollState = scrollState)
                3 -> SettingsScreen(viewModel = viewModel, scrollState = scrollState)
            }
        }
    }
}








