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
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.statusBarsPadding
import com.greedmitya.albcalculator.CraftViewModel
import com.greedmitya.albcalculator.R
import com.greedmitya.albcalculator.components.*
import com.greedmitya.albcalculator.model.BottomNavItemData

@Composable
fun CraftScreen(viewModel: CraftViewModel) {
    // Навигационные элементы
    val navItems = listOf(
        BottomNavItemData("Craft", R.drawable.ic_craft),
        BottomNavItemData("Favorites", R.drawable.ic_favorites),
        BottomNavItemData("How to use", R.drawable.ic_how_to_use),
        BottomNavItemData("Settings", R.drawable.ic_settings)
    )
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = AppColors.BackgroundDark,
        bottomBar = {
            Surface(
                color = Color(0xFF2A2A2A),
                tonalElevation = 4.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                BottomNavigationBar(
                    items = navItems,
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()              // учёт высоты статус-бара
                .padding(top = 60.dp, start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Заголовок
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TitleSection()
            }
            Spacer(Modifier.height(20.dp))

            // Селектор Зелий
            SelectorBlock(
                title = "Potion",
                options = viewModel.potions,
                selectedOption = viewModel.selectedPotion,
                onOptionSelected = { viewModel.onPotionSelected(it) },
                modifier = Modifier.fillMaxWidth(),
                menuMaxHeight = 240.dp
            )

            Spacer(Modifier.height(12.dp))

            // Tier и Enchantment
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SelectorBlock(
                    title = "Tier",
                    options = viewModel.availableTiers, // = [] если зелье не выбрано
                    selectedOption = viewModel.selectedTier,
                    onOptionSelected = { viewModel.selectedTier = it },
                    modifier = Modifier.weight(1f)
                )

                val enchantOptions = if (viewModel.selectedPotion != null) viewModel.enchantments else emptyList()

                SelectorBlock(
                    title = "Enchantment",
                    options = enchantOptions,
                    selectedOption = viewModel.selectedEnchantment,
                    onOptionSelected = { viewModel.selectedEnchantment = it },
                    modifier = Modifier.weight(1f)
                )


            }
            Spacer(Modifier.height(12.dp))

            // City и Fee
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SelectorBlock(
                    title = "City",
                    options = viewModel.cities,
                    selectedOption = viewModel.selectedCity,
                    onOptionSelected = { viewModel.selectedCity = it },
                    modifier = Modifier.weight(1f),
                )
                InputField(
                    title = "Fee for 100 nutrition",
                    value = viewModel.feePerNutritionInput,
                    onValueChange = { viewModel.feePerNutritionInput = it },
                    modifier = Modifier.weight(1f),
                    isNumeric = true
                )
            }

            Spacer(Modifier.height(24.dp))

            // Переключатели
            Row(
                Modifier.fillMaxWidth(),
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
            if (viewModel.useFocus) {
                Spacer(Modifier.height(12.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SmallInputField(
                        title = "Basic",
                        value = viewModel.focusBasic,
                        onValueChange = { viewModel.focusBasic = it },
                        modifier = Modifier.weight(1f)
                    )
                    SmallInputField(
                        title = "Mastery",
                        value = viewModel.focusMastery,
                        onValueChange = { viewModel.focusMastery = it },
                        modifier = Modifier.weight(1f)
                    )
                    SmallInputField(
                        title = "Total",
                        value = viewModel.focusTotal,
                        onValueChange = { viewModel.focusTotal = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            // Кнопки действия
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionTextButton(
                    text = "Calculate",
                    onClick = { viewModel.calculateProfit() },
                    modifier = Modifier.weight(1f)
                )
                ActionTextButton(
                    text = "Market",
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f)
                )
                ActionIconButton(
                    iconResId = R.drawable.button_dots_false, // ← замени на свою иконку
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}
