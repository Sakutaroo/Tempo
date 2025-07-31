package ca.ulaval.ima.mp.focus.presentation.active_focus.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray800

@Composable
fun ActiveFocusQuote(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "\"Focus helps you enter a flow state where you achieve more.\"",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray800,
            textAlign = TextAlign.Center
        )
    }
}
