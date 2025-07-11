package com.greedmitya.albcalculator.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun CoroutineScope.showTimedSnackbar(
    hostState: SnackbarHostState,
    message: String,
    durationMs: Long = 800
) {
    launch {
        hostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Indefinite
        )
    }

    launch {
        while (hostState.currentSnackbarData == null) {
            delay(10)
        }

        delay(durationMs)

        hostState.currentSnackbarData?.dismiss()
    }
}
