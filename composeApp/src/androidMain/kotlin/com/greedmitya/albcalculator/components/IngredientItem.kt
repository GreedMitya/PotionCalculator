package com.greedmitya.albcalculator.ui.components

import Ingredient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.assets.getDisplayNameFromItemId
import com.greedmitya.albcalculator.assets.loadIngredientImageBitmapById

// В файле IngredientItem.kt
@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onPriceChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(350.dp)
            .background(AppColors.Gray400, RoundedCornerShape(8.dp))
            .padding(bottom = 12.dp)
    ) {
        // Шапка с названием
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(29.dp)
                .background(AppColors.Gray500, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = getDisplayNameFromItemId(ingredient.name),
                color = AppColors.PrimaryGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(8.dp))

        // Основной ряд: картинка + колонка с двумя строками
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка
            Image(
                bitmap = loadIngredientImageBitmapById(ingredient.name),
                contentDescription = ingredient.name,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(15.dp))

            // Колонка с Cost и Quantity
            Column(modifier = Modifier.weight(1f)) {
                // — Cost per item —
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cost per item",
                        color = AppColors.BackgroundDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .background(AppColors.PanelBrown, RoundedCornerShape(8.dp))
                            .border(1.dp, AppColors.PrimaryGold, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        val priceText = ingredient.price?.toString() ?: ""
                        BasicTextField(
                            value = priceText,
                            onValueChange = onPriceChange,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(
                                color = AppColors.LightBeige,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif
                            ),
                            decorationBox = { inner ->
                                if (priceText.isEmpty()) {
                                    Text(
                                        text = "Enter",
                                        color = AppColors.LightBeige,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                                inner()
                            }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // — Quantity, выровненное под полем ввода —
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        color = AppColors.BackgroundDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .width(120.dp)      // та же ширина
                            .padding(start = 12.dp), // тот же left-padding
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = ingredient.quantity.toString(),
                            color = AppColors.LightBeige,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
            }
        }
    }
}



