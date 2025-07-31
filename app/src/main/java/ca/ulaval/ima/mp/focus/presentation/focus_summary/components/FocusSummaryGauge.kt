package ca.ulaval.ima.mp.focus.presentation.focus_summary.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoScoreGauge

@Composable
fun FocusSummaryGauge(
    focusScore: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TempoScoreGauge(
            score = focusScore.toInt()
        )
    }
}
