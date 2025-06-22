package com.greedmitya.albcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ToggleOption(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(AppColors.PanelBrown, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onCheckedChange(!checked) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = AppColors.PrimaryGold,
                fontSize = 16.sp
            )
            // Трек
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 20.dp)
                    .background(
                        color = if (checked) AppColors.ToggleGold else AppColors.Gray300,
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {
                // Сам ползунок
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
                        .background(
                            color = if (checked) AppColors.LightBeige else AppColors.LightBeige,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}
