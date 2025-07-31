package ca.ulaval.ima.mp.focus.presentation.focus_overview.components.session

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray900
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme

@Composable
fun SessionItem(
    title: String,
    duration: String,
    onSessionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onSessionClick()
            }
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Gray900,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = duration,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Gray900
        )
    }
}

@Preview
@Composable
private fun SessionItemPreview() {
    TempoTheme { 
        SessionItem(
            title = "PowerPoint Project",
            duration = "00:15",
            onSessionClick = {}
        )
    }
}
