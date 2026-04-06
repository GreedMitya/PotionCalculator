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
import org.jetbrains.compose.resources.stringResource
import potioncalculator.composeapp.generated.resources.Res
import potioncalculator.composeapp.generated.resources.how_to_use_api_note
import potioncalculator.composeapp.generated.resources.how_to_use_focus_basic
import potioncalculator.composeapp.generated.resources.how_to_use_focus_mastery
import potioncalculator.composeapp.generated.resources.how_to_use_focus_total
import potioncalculator.composeapp.generated.resources.how_to_use_step1
import potioncalculator.composeapp.generated.resources.how_to_use_step2
import potioncalculator.composeapp.generated.resources.how_to_use_step3
import potioncalculator.composeapp.generated.resources.how_to_use_step4
import potioncalculator.composeapp.generated.resources.how_to_use_subtitle
import potioncalculator.composeapp.generated.resources.title_potion_crafting

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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(Res.string.title_potion_crafting),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold
            )
            Text(
                text = stringResource(Res.string.how_to_use_subtitle),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold
            )
        }

        InstructionStep(
            number = 1,
            text = stringResource(Res.string.how_to_use_step1)
        )

        InstructionStep(
            number = 2,
            text = stringResource(Res.string.how_to_use_step2),
            imageResId = R.drawable.howto_step1
        )

        InstructionStep(
            number = 3,
            text = stringResource(Res.string.how_to_use_step3)
        )

        CenteredExplanation(stringResource(Res.string.how_to_use_focus_basic))
        InstructionImage(R.drawable.howto_step2)
        CenteredExplanation(stringResource(Res.string.how_to_use_focus_mastery))
        InstructionImage(R.drawable.howto_step3)
        CenteredExplanation(stringResource(Res.string.how_to_use_focus_total))
        InstructionImage(R.drawable.howto_step4)

        InstructionStep(
            number = 4,
            text = stringResource(Res.string.how_to_use_step4)
        )

        Text(
            text = stringResource(Res.string.how_to_use_api_note),
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



