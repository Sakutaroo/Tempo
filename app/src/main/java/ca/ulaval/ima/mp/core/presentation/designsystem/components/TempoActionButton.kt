package ca.ulaval.ima.mp.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme

@Composable
fun TempoActionButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Gray950,
    contentColor: Color = Gray50,
    enabled: Boolean = true,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 2.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            colors = ButtonColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = containerColor.copy(
                    alpha = 0.5f
                ),
                disabledContentColor = contentColor.copy(
                    alpha = 0.5f
                )
            ),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(
                width = borderWidth,
                color = borderColor
            ),
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                TempoLoader(
                    modifier = Modifier
                        .size(15.dp)
                        .alpha(if (isLoading) 1f else 0f),
                    color = contentColor
                )
                Text(
                    text = text,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .alpha(if (isLoading) 0f else 1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = contentColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun TempoActionButtonPreview() {
    TempoTheme {
        TempoActionButton(
            text = "Start",
            isLoading = false,
            onClick = { }
        )
    }
}
