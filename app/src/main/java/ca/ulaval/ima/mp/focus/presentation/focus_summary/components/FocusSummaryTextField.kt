package ca.ulaval.ima.mp.focus.presentation.focus_summary.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoTextField

@Composable
fun FocusSummaryTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TempoTextField(
            state = state,
            hint = stringResource(R.string.enter_a_description_for_today_s_session),
            title = stringResource(R.string.focused_to),
            imeAction = ImeAction.Done
        )
    }
}
