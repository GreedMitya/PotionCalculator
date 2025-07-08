package com.greedmitya.albcalculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedmitya.albcalculator.components.AppColors

@Composable
fun HowToUseScreen(scrollState: ScrollState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BackgroundDark)
            .padding(top = 60.dp, bottom = 30.dp, start = 40.dp, end = 40.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Шапка
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Potion Crafting",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold
            )
            Text(
                text ="How to Use",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold
            )
        }

        // Шаги
        InstructionStep(
            number = 1,
            text = "Select the potion you want to craft along with its tier and enchantment level."
        )

        InstructionStep(
            number = 2,
            text = "Then choose the city where you’ll craft and enter the fee per 100 nutrition.",
            imageResId = R.drawable.howto_step1
        )

        InstructionStep(
            number = 3,
            text = "Enable the Premium Account toggle if you have one, and activate Focus Points if you plan to use them."
        )

        CenteredExplanation("Basic = general crafting level")
        InstructionImage(R.drawable.howto_step2)
        CenteredExplanation("Mastery = specialization for this potion type")
        InstructionImage(R.drawable.howto_step3)
        CenteredExplanation("Total = total mastery for this branch")
        InstructionImage(R.drawable.howto_step4)

        InstructionStep(
            number = 4,
            text = "Enter the ingredient prices and the output potion price, then tap \"Calculate\" to view your profit.\nYou can also tap \"Market Prices\" to fetch in-game prices via API."
        )

        Text(
            text = "Note: API prices may not reflect real-time market changes.",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.SansSerif,
            lineHeight = 30.sp
        )
    }
}

@Composable
fun CenteredExplanation(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 30.sp,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}

@Composable
fun InstructionStep(number: Int, text: String, imageResId: Int? = null) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "$number. $text",
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.SansSerif,
            lineHeight = 30.sp,
            textAlign = TextAlign.Start
        )
        imageResId?.let { InstructionImage(it) }
    }
}

@Composable
fun InstructionImage(imageResId: Int) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}



