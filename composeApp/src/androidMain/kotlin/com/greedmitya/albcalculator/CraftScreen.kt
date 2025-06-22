package com.greedmitya.albcalculator.ui.screens.craft

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.greedmitya.albcalculator.R
import com.greedmitya.albcalculator.CraftViewModel
import com.greedmitya.albcalculator.components.*
import com.greedmitya.albcalculator.model.BottomNavItemData

@Composable
fun CraftScreen(viewModel: CraftViewModel) {
    // Данные для нижней навигации
    val navItems = listOf(
        BottomNavItemData("Craft", R.drawable.ic_craft),
        BottomNavItemData("Favorites", R.drawable.ic_favorites),
        BottomNavItemData("How to use", R.drawable.ic_how_to_use),
        BottomNavItemData("Settings", R.drawable.ic_settings)
    )

    // Состояние выбранного пункта меню
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = AppColors.BackgroundDark,
        bottomBar = {
            // Фон для нижней панели: #2A2A2A
            Surface(
                color = Color(0xFF2A2A2A),
                tonalElevation = 4.dp
            ) {
                BottomNavigationBar(
                    items = navItems,
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    ) { innerPadding ->
        // Основной контент с учётом паддинга от Scaffold, но без нижнего отступа
        Column(
            modifier = Modifier
                .fillMaxSize()
                // 1) сначала «отодвигаем» контент от нижней панели
                .padding(innerPadding)
                // 2) потом задаём свои отступы: топ = 60dp, боковые = 40dp
                .padding(top = 60.dp, start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        )  {
            // Заголовок по центру
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TitleSection()
            }

            Spacer(Modifier.height(20.dp))
            SelectorBlock(title = "Potion")
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SelectorBlock(title = "Tier", modifier = Modifier.weight(1f))
                SelectorBlock(title = "Enchantment", modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SelectorBlock(title = "City", modifier = Modifier.weight(1f))
                SelectorBlock(title = "Fee for 100 nutrition", modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ToggleOption(
                    label = "Premium",
                    checked = viewModel.isPremium,
                    onCheckedChange = { viewModel.isPremium = it },
                    modifier = Modifier.weight(1f)
                )
                ToggleOption(
                    label = "Use focus",
                    checked = viewModel.useFocus,
                    onCheckedChange = { viewModel.useFocus = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionButton(text = "Calculate") { viewModel.calculateProfit() }
                ActionButton(text = "Market") { /* placeholder */ }
                SquareIconPlaceholder()
            }
        }
    }
}
