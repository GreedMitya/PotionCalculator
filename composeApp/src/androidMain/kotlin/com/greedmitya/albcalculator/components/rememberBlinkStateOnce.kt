package com.greedmitya.albcalculator.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun rememberBlinkStateForKey(key: Any?, durationMs: Int = 300): Boolean {
    var shouldBlink by remember { mutableStateOf(false) }

    LaunchedEffect(key) {
        if (key != null) {
            shouldBlink = true
            delay(durationMs.toLong())
            shouldBlink = false
        }
    }

    return shouldBlink
}

