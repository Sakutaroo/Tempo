package ca.ulaval.ima.mp.core.presentation.designsystem.modifiers

import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.debugBorder(
    width: Dp = 1.dp,
    color: Color = Color.Red
) = this.border(
    width = width,
    color = color
)
