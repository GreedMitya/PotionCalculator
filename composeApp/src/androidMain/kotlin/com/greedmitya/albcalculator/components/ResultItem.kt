package com.greedmitya.albcalculator.components

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedmitya.albcalculator.assets.loadPotionImageBitmapFromDisplayName
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.ui.theme.EBGaramond
import kotlinx.coroutines.delay


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
    isError: Boolean = false,
    isBlinking: Boolean = false,
    shimmerColor: Color = Color.Transparent,
    modifier: Modifier = Modifier,
    onCopy: () -> Unit = {}
) {
    val clipboardManager = LocalClipboardManager.current

    var hasAppeared by remember { mutableStateOf(false) }
    var blinkState by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        hasAppeared = true
    }

    LaunchedEffect(isBlinking, hasAppeared) {
        if (isBlinking && hasAppeared) {
            blinkState = true
            delay(300)
            blinkState = false
        }
    }

    LaunchedEffect(isError, hasAppeared) {
        if (isError && hasAppeared) {
            localError = true
            delay(3000)
            localError = false
        }
    }

    Column(
        modifier = modifier
            .width(350.dp)
            .background(AppColors.Gray400, RoundedCornerShape(8.dp))
            .padding(bottom = 12.dp)
    ) {
        // Header
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
                text = potionDisplayName,
                color = AppColors.PrimaryGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
            )
            Image(
                painter = painterResource(R.drawable.clone),
                contentDescription = "Copy Potion Name",
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(potionDisplayName))
                        onCopy()
                    }
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon
            Image(
                bitmap = loadPotionImageBitmapFromDisplayName(potionDisplayName, tier, enchantment),
                contentDescription = potionDisplayName,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f).padding(top = 4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Price per Item",
                        color = AppColors.BackgroundDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.weight(1f)
                    )

                    val borderColor by rememberBlinkingError(isError && hasAppeared)
                    //val blinkState = rememberBlinkStateForKey(isBlinkKey)
                    val blinkOverlayColor by animateColorAsState(
                        targetValue = if (isError) AppColors.PanelBrown.copy(alpha = 0.8f) else AppColors.PanelBrown,
                        animationSpec = tween(durationMillis = 400)
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
                                .background(blinkOverlayColor, RoundedCornerShape(8.dp))
                        )

                        BasicTextField(
                            value = pricePerItem,
                            onValueChange = onPriceChange,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(
                                color = AppColors.LightBeige,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif,
                            ),
                            decorationBox = { inner ->
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (pricePerItem.isEmpty()) {
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
                ResultRow("Quantity per Craft", quantity.toString())
                Spacer(Modifier.height(12.dp))
                ResultRow("Profit", profitSilver, shimmerColor)
                Spacer(Modifier.height(12.dp))
                ResultRow("Profit %", profitPercent, shimmerColor)
            }
        }
    }
}


@Composable
fun ResultRow(
    label: String,
    value: String,
    shimmerColor: Color = Color.Transparent
) {
    val (finalLabel, labelColor) = remember(label, value) {
        if (label == "Profit %" || label == "Profit") {
            val percent = value.filter { it.isDigit() || it == '.' || it == '-' }.toFloatOrNull()
            val arrow = when {
                percent == null -> ""
                percent >= 0f -> " ▲"
                else -> " ▼"
            }
            val color = when {
                percent == null -> AppColors.BackgroundDark
                percent >= 0f -> Color(0xFF4CAF50)
                else -> Color(0xFFFF5252)
            }
            label + arrow to color
        } else {
            label to AppColors.BackgroundDark
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = finalLabel,
            color = labelColor,
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
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(shimmerColor, RoundedCornerShape(4.dp))
            )

            Text(
                text = value,
                color = AppColors.LightBeige,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
            )
        }
    }
}




