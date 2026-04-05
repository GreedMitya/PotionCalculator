package com.greedmitya.albcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.model.BottomNavItemData

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItemData>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    barHeight: Dp = 72.dp,
    showLabels: Boolean = true
) {
    Row(
        modifier = modifier
            .height(barHeight)
            .background(Color(0xFF2A2A2A))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            val iconPainter = if (isSelected && item.selectedIconResId != null)
                painterResource(id = item.selectedIconResId)
            else
                painterResource(id = item.iconResId)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelect(index) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = item.label,
                    modifier = Modifier.fillMaxWidth(),
                    tint = Color.Unspecified
                )
                if (item.showExternalLabel && showLabels) {
                    Text(
                        text = item.label,
                        color = if (isSelected) Color(0xFFC47A30) else Color(0xFF999999),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}


