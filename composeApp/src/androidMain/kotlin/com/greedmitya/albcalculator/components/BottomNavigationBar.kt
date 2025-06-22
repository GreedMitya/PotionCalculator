package com.greedmitya.albcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
    barHeight: Dp = 72.dp,       // стандартная высота
    showLabels: Boolean = false  // по умолчанию без подписей
) {
    Row(
        modifier = modifier
            .height(barHeight)                   // задаём высоту
            .background(Color(0xFF2A2A2A))      // фон здесь или из Surface
            .padding(horizontal = 30.dp, vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            Column(
                modifier = Modifier
                    .clickable { onSelect(index) }
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = item.iconResId),
                    contentDescription = item.label,
                    modifier = Modifier.size(84.dp),  // размер иконки
                    tint = Color.Unspecified
                )
                if (showLabels) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = item.label,
                        color = Color(0xFFF2E9DC),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

