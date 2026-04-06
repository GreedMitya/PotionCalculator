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
import com.greedmitya.albcalculator.ui.theme.EBGaramond

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

            if (item.showExternalLabel && showLabels) {
                // Box overlay: text sits inside the background shape (ic_markets.xml is 82×57dp)
                // Active text = #2A2A2A on orange, Inactive = #F2E9DC on dark — mirrors other icon SVGs
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(index) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = iconPainter,
                        contentDescription = item.label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp),
                        tint = Color.Unspecified,
                    )
                    Text(
                        text = item.label,
                        color = if (isSelected) Color(0xFF2A2A2A) else Color(0xFFF2E9DC),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = EBGaramond,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 6.dp),
                    )
                }
            } else {
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
                        tint = Color.Unspecified,
                    )
                }
            }
        }
    }
}


