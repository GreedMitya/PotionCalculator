package com.greedmitya.albcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.R
import com.greedmitya.albcalculator.ui.theme.EBGaramond

@Composable
fun CraftSubTabRow(
    selectedIndex: Int,
    isPremiumUnlocked: Boolean,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(AppColors.Gray500)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        CraftSubTab(
            label = "Craft",
            isSelected = selectedIndex == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f),
        )
        CraftSubTab(
            label = "Craft+",
            isSelected = selectedIndex == 1,
            onClick = { onTabSelected(1) },
            showLock = !isPremiumUnlocked,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun CraftSubTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showLock: Boolean = false,
) {
    val bgColor = if (isSelected) AppColors.PrimaryGold else AppColors.Gray500
    val textColor = if (isSelected) AppColors.BackgroundDark else AppColors.Gray300

    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = label,
                color = textColor,
                fontFamily = EBGaramond,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
            if (showLock) {
                Icon(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = "Locked",
                    tint = textColor,
                    modifier = Modifier.size(14.dp),
                )
            }
        }
    }
}
