package com.greedmitya.albcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.greedmitya.albcalculator.R

@Composable
fun SelectorBlock(
    title: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier ,
    menuMaxHeight: Dp? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectorWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = AppColors.PrimaryGold
        )
        Spacer(Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    val pxWidth = coordinates.size.width
                    selectorWidth = with(density) { pxWidth.toDp() }
                }
                .border(
                    width = 1.dp,
                    color = AppColors.PrimaryGold,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(AppColors.PanelBrown, RoundedCornerShape(8.dp))
                .clickable { expanded = !expanded }
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedOption ?: "Choose",
                    color = AppColors.White,
                    fontSize = 16.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.chevron_down),
                    contentDescription = "Expand dropdown",
                    modifier = Modifier.size(16.dp),
                    tint = AppColors.White
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(selectorWidth)
                .then(
                    if (menuMaxHeight != null) Modifier.heightIn(max = menuMaxHeight) else Modifier
                )
                .background(AppColors.LightBeige, RoundedCornerShape(8.dp))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            fontSize = 16.sp,
                            color = Color(0xFF333333)
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
