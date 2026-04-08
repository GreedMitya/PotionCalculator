package com.greedmitya.albcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import org.jetbrains.compose.resources.stringResource
import potioncalculator.composeapp.generated.resources.*
import potioncalculator.composeapp.generated.resources.Res
import potioncalculator.composeapp.generated.resources.button_buy_premium
import potioncalculator.composeapp.generated.resources.button_restore
import potioncalculator.composeapp.generated.resources.premium_badge
import potioncalculator.composeapp.generated.resources.premium_disclaimer
import potioncalculator.composeapp.generated.resources.premium_feature_advisor
import potioncalculator.composeapp.generated.resources.premium_feature_batch
import potioncalculator.composeapp.generated.resources.premium_feature_focus
import potioncalculator.composeapp.generated.resources.premium_feature_monitor
import potioncalculator.composeapp.generated.resources.premium_features_header
import potioncalculator.composeapp.generated.resources.premium_title

@Composable
fun PremiumUpgradeScreen(
    onBuyClick: () -> Unit,
    onRestoreClick: () -> Unit,
    premiumPrice: String? = null,
    isPurchasing: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp, start = 30.dp, end = 30.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_lock),
            contentDescription = null,
            tint = AppColors.PrimaryGold,
            modifier = Modifier.size(48.dp),
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.premium_badge),
            color = AppColors.PrimaryGold,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        )

        Text(
            text = stringResource(Res.string.premium_title),
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
                text = stringResource(Res.string.premium_features_header),
                color = AppColors.PrimaryGold,
                fontFamily = EBGaramond,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
            )

            FeatureRow(stringResource(Res.string.premium_feature_focus))
            FeatureRow(stringResource(Res.string.premium_feature_batch))
            FeatureRow(stringResource(Res.string.premium_feature_monitor))
            FeatureRow(stringResource(Res.string.premium_feature_advisor))
        }

        Spacer(Modifier.height(32.dp))

        if (premiumPrice != null) {
            PriceBadge(price = premiumPrice)
            Spacer(Modifier.height(16.dp))
        } else {
            Spacer(Modifier.height(48.dp))
        }

        ActionTextButton(
            text = if (isPurchasing) stringResource(Res.string.premium_purchasing) else stringResource(Res.string.button_buy_premium),
            onClick = onBuyClick,
            enabled = !isPurchasing,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = if (isPurchasing) AppColors.Gray500 else AppColors.PrimaryGold,
            textColor = if (isPurchasing) AppColors.Gray300 else AppColors.BackgroundDark,
            borderColor = if (isPurchasing) AppColors.Gray400 else AppColors.PrimaryGold,
        )

        Spacer(Modifier.height(12.dp))

        ActionTextButton(
            text = stringResource(Res.string.button_restore),
            onClick = onRestoreClick,
            enabled = !isPurchasing,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = AppColors.Gray500,
            textColor = if (isPurchasing) AppColors.Gray400 else AppColors.Secondary_Beige,
            borderColor = if (isPurchasing) AppColors.Gray400 else AppColors.Secondary_Beige.copy(alpha = 0.5f),
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.premium_disclaimer),
            color = AppColors.Gray300,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PriceBadge(price: String) {
    Box(
        modifier = Modifier
            .background(
                color = AppColors.PrimaryGold.copy(alpha = 0.12f),
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = AppColors.PrimaryGold.copy(alpha = 0.6f),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 24.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = price,
            color = AppColors.PrimaryGold,
            fontFamily = EBGaramond,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            letterSpacing = 1.sp,
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
