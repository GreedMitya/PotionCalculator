package com.greedmitya.albcalculator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PotionCalculator",
    ) {
        App()
    }
}