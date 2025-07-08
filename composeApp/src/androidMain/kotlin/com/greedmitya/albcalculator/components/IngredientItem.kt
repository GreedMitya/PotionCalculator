package com.greedmitya.albcalculator.ui.components

import Ingredient
import com.greedmitya.albcalculator.R
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.assets.getDisplayNameFromItemId
import com.greedmitya.albcalculator.assets.loadIngredientImageBitmapById
import com.greedmitya.albcalculator.components.rememberBlinkingError
import com.greedmitya.albcalculator.ui.theme.EBGaramond
import kotlinx.coroutines.delay

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onPriceChange: (String) -> Unit,
    onCopy: () -> Unit = {},
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val displayName = getDisplayNameFromItemId(ingredient.name)


    Column(
        modifier = modifier
            .width(350.dp)
            .background(AppColors.Gray400, RoundedCornerShape(8.dp))
            .padding(bottom = 12.dp)
    ) {
        // Header с копированием
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(29.dp)
                .background(AppColors.Gray500, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(horizontal = 12.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = displayName,
                color = AppColors.PrimaryGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
            )

            Image(
                painter = painterResource(R.drawable.clone),
                contentDescription = "Copy Ingredient Name",
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(displayName))
                        onCopy()
                    }
            )
        }

        Spacer(Modifier.height(8.dp))

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

            Spacer(Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                val borderColor by rememberBlinkingError(isError)
                val blinkOverlayColor by animateColorAsState(
                    targetValue = if (isError) AppColors.PanelBrown.copy(alpha = 0.8f) else AppColors.PanelBrown,
                    animationSpec = tween(durationMillis = 400)
                )



                // Цена
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween // ⬅️ фикс
                ) {
                    Text(
                        text = "Price per Item",
                        color = AppColors.BackgroundDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif,
                        softWrap = true, // ✅ разрешаем перенос
                        maxLines = 2,    // ✅ максимум 2 строки
                        modifier = Modifier
                            .fillMaxWidth(0.4f) // ✅ ограничиваем ширину, чтобы он знал когда переносить
                    )
                Box(
                        modifier = Modifier
                            .width(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppColors.PanelBrown)
                            .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(8.dp))
                                .background(blinkOverlayColor)
                        )

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
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
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
                            }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Кол-во
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        color = AppColors.BackgroundDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .padding(start = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = ingredient.quantity.toString(),
                            color = AppColors.LightBeige,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Serif,
                        )
                    }
                }
            }
        }
    }
}
