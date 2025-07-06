package com.greedmitya.albcalculator.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.State
import kotlinx.coroutines.delay

@Composable
fun rememberBlinkingError(isErrorTrigger: Boolean): State<Color> {
    var showError by remember { mutableStateOf(false) }

    // Этот эффект отменяется при повторном запуске
    LaunchedEffect(isErrorTrigger) {
        if (isErrorTrigger) {
            showError = true
            delay(300)
            showError = false
            delay(300)
            showError = true
            delay(1000)
            showError = false
        } else {
            showError = false // сбрасываем мгновенно
        }
    }

    return animateColorAsState(
        targetValue = if (showError) Color.Red else AppColors.PrimaryGold,
        animationSpec = tween(durationMillis = 300),
        label = "blinkColor"
    )
}


