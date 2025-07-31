package ca.ulaval.ima.mp.focus.presentation.focus_summary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray200
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.focus.presentation.models.SessionUi

@Composable
fun FocusSummaryCard(
    descriptionState: TextFieldState,
    sessionUi: SessionUi,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Gray200,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 8.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        FocusSummaryDuration(
            duration = sessionUi.duration
        )
        FocusSummaryGauge(
            focusScore = sessionUi.focusScore
        )
        FocusSummaryTextField(
            state = descriptionState
        )
    }
}

@Preview
@Composable
private fun FocusSummaryCardPreview() {
    TempoTheme {
        FocusSummaryCard(
            descriptionState = TextFieldState(""),
            sessionUi = SessionUi(
                id = "0",
                duration = "01:30",
                description = stringResource(R.string.work_session)
            )
        )
    }
}
