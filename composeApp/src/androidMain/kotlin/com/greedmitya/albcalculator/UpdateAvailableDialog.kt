package com.greedmitya.albcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.greedmitya.albcalculator.components.AppColors

@Composable
fun UpdateAvailableDialog(
    whatsNew: List<String>,
    onUpdateNow: () -> Unit,
    onLater: () -> Unit,
) {
    Dialog(onDismissRequest = onLater) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(AppColors.BackgroundDark)
                .border(1.dp, AppColors.PrimaryGold, RoundedCornerShape(12.dp))
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "✦  New Update Available",
                color = AppColors.PrimaryGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "What's new:",
                    color = AppColors.LightBeige,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                )
                whatsNew.forEach { item ->
                    Text(
                        text = "•  $item",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        lineHeight = 22.sp,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
            ) {
                TextButton(onClick = onLater) {
                    Text(
                        text = "Later",
                        color = AppColors.Gray300,
                    )
                }
                TextButton(onClick = onUpdateNow) {
                    Text(
                        text = "Update Now",
                        color = AppColors.PrimaryGold,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}
