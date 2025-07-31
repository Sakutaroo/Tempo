package ca.ulaval.ima.mp.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray900
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme

@Composable
fun TempoDialog(
    title: String,
    onDismiss: () -> Unit,
    description: String,
    primaryButton: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    secondaryButton: @Composable RowScope.() -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Gray50)
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Gray950
            )
            Text(
                text = description,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Gray900
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                secondaryButton()
                primaryButton()
            }
        }
    }
}

@Preview
@Composable
private fun TempoDialogPreview() {
    TempoTheme {
        TempoDialog(
            title = "Activity Permission",
            onDismiss = {},
            description = "Is it ok for you?",
            primaryButton = {
                TempoActionButton(
                    text = "No problem",
                    isLoading = false,
                    onClick = {}
                )
            }
        )
    }
}
