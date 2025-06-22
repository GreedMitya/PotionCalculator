package com.greedmitya.albcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ActionButton(text: String, function: () -> Unit) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .background(Color(0xFFCCCCCC), RoundedCornerShape(8.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color(0xFF999999), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SquareIconPlaceholder() {
    Box(
        modifier = Modifier
            .size(46.dp)
            .background(Color(0xFFCCCCCC), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(16.dp)) {}
    }
}
