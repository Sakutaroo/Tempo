package ca.ulaval.ima.mp.focus.presentation.active_focus.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray900

@Composable
fun ActiveFocusDuration(
    duration: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                duration.forEach { char ->
                    if (char.isDigit()) {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 96.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        ) {
                            append(char)
                        }
                    } else {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(char)
                        }
                    }
                }
            },
            color = Gray900
        )
    }
}
