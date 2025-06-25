package com.greedmitya.albcalculator.assets

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
fun loadAssetPainter(
    id: String,
    folder: String, // "ingredients" или "potions"
    fallback: String = "default.png"
): Painter {
    return try {
        painterResource("assets/$folder/${id}.png")
    } catch (e: Exception) {
        painterResource("assets/$folder/$fallback")
    }
}
