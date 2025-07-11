package com.greedmitya.albcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.greedmitya.albcalculator.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.greedmitya.albcalculator.ui.theme.EBGaramond


@Composable
fun ActionTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color,
    textColor: Color,
    borderColor: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val bgColor = when {
        isPressed -> backgroundColor.copy(alpha = 0.8f)
        else -> backgroundColor
    }

    Box(
        modifier = modifier
            .height(48.dp)
            .widthIn(min = 100.dp, max = 160.dp)
            .background(bgColor, RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ActionIconMenuButton(
    iconResId: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onResetClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = if (isPressed) AppColors.PrimaryGold else AppColors.Gray200




    var showConfirmPopup by remember { mutableStateOf(false) }

    Box(modifier) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(backgroundColor, RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled
                ) {
                    expanded = !expanded
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp)
            )
        }

        if (expanded) {
            Popup(
                alignment = Alignment.BottomEnd,
                offset = IntOffset(0, -130),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                Column(
                    modifier = Modifier
                        .background(AppColors.PanelBrown, RoundedCornerShape(12.dp))
                        .padding(vertical = 4.dp)
                        .width(IntrinsicSize.Max)
                ) {
                    DropdownMenuItemCustom(
                        icon = painterResource(R.drawable.ic_reset),
                        text = "Reset all prices",
                        onClick = {
                            expanded = false
                            onResetClick()
                        }
                    )
                    DropdownMenuItemCustom(
                        icon = painterResource(R.drawable.ic_bookmark),
                        text = "Save to Favorites",
                        onClick = {
                            expanded = false
                            showConfirmPopup = true
                        }
                    )
                }
            }
        }
        if (showConfirmPopup) {
        SaveToFavoritesPopup(
            onDismiss = { showConfirmPopup = false },
            onConfirm = {
                showConfirmPopup = false
                onSaveClick()
            }
        )
    }
    }
}

@Composable
fun DropdownMenuItemCustom(
    icon: Painter,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = AppColors.PrimaryGold,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            color = AppColors.PrimaryGold,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    }
}




