package ca.ulaval.ima.mp.focus.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoLabeledValueItem

@Composable
fun FocusLabeledItems(
    focusedTimeAvg: String,
    bestFocusScore: String,
    bestTime: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TempoLabeledValueItem(
            label = stringResource(R.string.average_time),
            value = focusedTimeAvg,
            modifier = Modifier
                .weight(1f)
        )
        TempoLabeledValueItem(
            label = stringResource(R.string.best_focus),
            value = bestFocusScore,
            modifier = Modifier
                .weight(1f)
        )
        TempoLabeledValueItem(
            label = stringResource(R.string.best_time),
            value = bestTime,
            modifier = Modifier
                .weight(1f)
        )
    }
}
