package com.greedmitya.albcalculator.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

@Composable
fun TitleSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(140.dp),
        horizontalAlignment = Alignment.CenterHorizontally  // ← вот оно
    ) {
        Text(
            text = "Potion Crafting",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFC47A30)
        )
        Text(
            text = "Profit Calculator",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFC47A30)
        )
    }
}
