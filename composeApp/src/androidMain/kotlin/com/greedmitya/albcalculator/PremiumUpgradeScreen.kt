package com.greedmitya.albcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.components.ActionTextButton
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.ui.theme.EBGaramond

@Composable
fun PremiumUpgradeScreen(
    onBuyClick: () -> Unit,
    onRestoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 60.dp, start = 30.dp, end = 30.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(40.dp))

        Icon(
            painter = painterResource(R.drawable.ic_lock),
            contentDescription = null,
            tint = AppColors.PrimaryGold,
            modifier = Modifier.size(48.dp),
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "CRAFT+",
            color = AppColors.PrimaryGold,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        )

        Text(
            text = "Unlock Premium Features",
            color = AppColors.Secondary_Beige,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )

        Spacer(Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Gray500, RoundedCornerShape(12.dp))
                .border(1.dp, AppColors.PrimaryGold.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "What you get:",
                color = AppColors.PrimaryGold,
                fontFamily = EBGaramond,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
            )

            FeatureRow("Focus Crafting calculator")
            FeatureRow("Batch quantity mode")
            FeatureRow("City Price Monitor across all cities")
            FeatureRow("Potion Profit Advisor")
        }

        Spacer(Modifier.height(40.dp))

        ActionTextButton(
            text = "Buy Premium",
            onClick = onBuyClick,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = AppColors.PrimaryGold,
            textColor = AppColors.BackgroundDark,
            borderColor = AppColors.PrimaryGold,
        )

        Spacer(Modifier.height(12.dp))

        ActionTextButton(
            text = "Restore Purchase",
            onClick = onRestoreClick,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = AppColors.Gray500,
            textColor = AppColors.Secondary_Beige,
            borderColor = AppColors.Secondary_Beige.copy(alpha = 0.5f),
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "One-time purchase. No subscription.",
            color = AppColors.Gray300,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun FeatureRow(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "+",
            color = AppColors.PrimaryGold,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Text(
            text = text,
            color = AppColors.LightBeige,
            fontSize = 14.sp,
        )
    }
}
