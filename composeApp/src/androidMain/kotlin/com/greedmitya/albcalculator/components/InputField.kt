package com.greedmitya.albcalculator.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isNumeric: Boolean = true,
    enabled: Boolean = true // ← добавляем параметр

) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = 350.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = AppColors.PrimaryGold
        )
        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text
            ),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = AppColors.White,
                fontSize = 16.sp,
                lineHeight = 20.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = AppColors.White,
                unfocusedTextColor = AppColors.White,
                focusedContainerColor = AppColors.PanelBrown,
                unfocusedContainerColor = AppColors.PanelBrown,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = AppColors.PrimaryGold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp) // чуть выше 40, чтобы не было обрезания
                .border(1.dp, AppColors.PrimaryGold, RoundedCornerShape(8.dp))
                .padding(horizontal = 0.dp) // вертикально — управляет TextField сам
        )
    }
}
