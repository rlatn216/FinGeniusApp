package com.skim.core.designsystem.component


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skim.core.designsystem.theme.divider1Color


@Composable
fun TopBarDivider(
    dividerColor: Color = MaterialTheme.colors.divider1Color,
    height: Dp = 24.dp,
    width: Dp = 1.dp
) {
    Divider(
        modifier = Modifier
            .height(height)
            .width(width),
        color = dividerColor
    )
}