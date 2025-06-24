package com.greedmitya.albcalculator.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SmallInputField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.widthIn(min = 80.dp, max = 120.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = AppColors.PrimaryGold
        )
        Spacer(Modifier.height(4.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = AppColors.White
            ),
            shape = RoundedCornerShape(6.dp),
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
                .height(48.dp)
                .border(1.dp, AppColors.PrimaryGold, RoundedCornerShape(6.dp))
        )
    }
}
