package ca.ulaval.ima.mp.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray800
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray900
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme

@Composable
fun TempoLabeledValueItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            color = Gray900,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        )
        HorizontalDivider(
            modifier = Modifier
                .width(110.dp),
            color = Gray800,
        )
        Text(
            text = value,
            color = Gray950,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp
        )
    }
}

@Preview
@Composable
private fun TempoLabeledValueItemPreview() {
    TempoTheme { 
        TempoLabeledValueItem(
            label = "Focused",
            value = "15 m"
        )
    }
}
