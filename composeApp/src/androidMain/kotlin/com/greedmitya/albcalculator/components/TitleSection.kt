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
import org.jetbrains.compose.resources.stringResource
import potioncalculator.composeapp.generated.resources.Res
import potioncalculator.composeapp.generated.resources.component_title_profit_calculator
import potioncalculator.composeapp.generated.resources.title_potion_crafting

@Composable
fun TitleSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.title_potion_crafting),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFC47A30)
        )
        Text(
            text = stringResource(Res.string.component_title_profit_calculator),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFC47A30)
        )
    }
}
