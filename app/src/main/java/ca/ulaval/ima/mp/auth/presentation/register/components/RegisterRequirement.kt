package ca.ulaval.ima.mp.auth.presentation.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme

@Composable
fun RegisterRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = if (isValid) {
                Icons.Filled.Check
            } else {
                Icons.Filled.Close
            },
            contentDescription = null,
            tint = if (isValid) {
                Color.Green
            } else {
                Color.Red
            }
        )
        Text(
            text = text,
            color = Gray950,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun RegisterRequirementPreview() {
    TempoTheme {
        RegisterRequirement(
            text = "Must contain 6 characters",
            isValid = true
        )
    }
}
