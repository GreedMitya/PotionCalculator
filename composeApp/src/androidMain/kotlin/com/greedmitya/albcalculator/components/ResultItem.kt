package com.greedmitya.albcalculator.components

import com.greedmitya.albcalculator.R
import android.content.ClipData
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.ui.theme.EBGaramond
import com.greedmitya.albcalculator.assets.loadPotionImageBitmapFromDisplayName
import com.greedmitya.albcalculator.components.AppColors
import kotlinx.coroutines.launch


@Composable
fun ResultItem(
    potionDisplayName: String,
    tier: String,
    enchantment: Int,
    pricePerItem: String,
    onPriceChange: (String) -> Unit,
    profitSilver: String,
    profitPercent: String,
    quantity: Int,
    isBlinking: Boolean,
    shimmerColor: Color,
    isError: Boolean,
    modifier: Modifier = Modifier,
    craftQuantity: Int = 1,
    totalProfit: String = "",
    onCopy: () -> Unit = {},
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    // Анимация мерцания при расчете (твоя фича)
    val blinkColor by animateColorAsState(
        targetValue = if (isBlinking) shimmerColor else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        modifier = modifier
            .width(350.dp)
            .background(AppColors.Gray400, RoundedCornerShape(8.dp))
            .padding(bottom = 12.dp)
    ) {
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
                text = "$potionDisplayName ($tier.$enchantment)",
                color = AppColors.PrimaryGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = EBGaramond,
            )

            Image(
                painter = painterResource(R.drawable.clone),
                contentDescription = "Copy Potion Name",
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        scope.launch {
                            clipboard.setClipEntry(ClipEntry(ClipData.newPlainText(null, potionDisplayName)))
                        }
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
            Image(
                bitmap = loadPotionImageBitmapFromDisplayName(potionDisplayName, tier, enchantment),
                contentDescription = potionDisplayName,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Sell Price Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Price per Potion",
                        color = AppColors.BackgroundDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = EBGaramond,
                        modifier = Modifier.fillMaxWidth(0.4f)
                    )
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppColors.PanelBrown)
                            .border(1.dp, if (isError) Color.Red else Color.Transparent, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            value = pricePerItem,
                            onValueChange = onPriceChange,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(
                                color = AppColors.LightBeige,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = EBGaramond
                            ),
                            decorationBox = { inner ->
                                Box(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (pricePerItem.isEmpty()) {
                                        Text(
                                            text = "Enter",
                                            color = AppColors.LightBeige,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                            fontFamily = EBGaramond
                                        )
                                    }
                                    inner()
                                }
                            }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Quantity
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        color = AppColors.BackgroundDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = EBGaramond,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .padding(start = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        val qtyText = if (craftQuantity > 1) {
                            "${quantity * craftQuantity} (${quantity}x$craftQuantity)"
                        } else {
                            quantity.toString()
                        }
                        Text(
                            text = qtyText,
                            color = AppColors.LightBeige,
                            fontSize = if (craftQuantity > 1) 13.sp else 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = EBGaramond,
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Profit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profit",
                        color = AppColors.BackgroundDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = EBGaramond,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .padding(start = 12.dp)
                            .border(2.dp, blinkColor, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column {
                            Text(
                                text = profitSilver,
                                color = if (totalProfit == "") AppColors.LightBeige else if (profitSilver.startsWith("-")) Color(0xFFF44336) else Color(0xFF4CAF50),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = EBGaramond,
                            )
                            Text(
                                text = "($profitPercent)",
                                color = if (totalProfit == "") AppColors.LightBeige else if (profitPercent.startsWith("-")) Color(0xFFF44336) else Color(0xFF4CAF50),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = EBGaramond,
                            )
                        }
                    }
                }

                if (craftQuantity > 1) {
                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Total ($craftQuantity runs)",
                            color = AppColors.BackgroundDark,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = EBGaramond,
                            modifier = Modifier.weight(1f),
                        )
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .padding(start = 12.dp),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(
                                text = totalProfit,
                                color = if (totalProfit == "") AppColors.LightBeige else if (totalProfit.startsWith("-")) Color(0xFFF44336) else Color(0xFF4CAF50),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = EBGaramond,
                            )
                        }
                    }
                }
            }
        }
    }
}
