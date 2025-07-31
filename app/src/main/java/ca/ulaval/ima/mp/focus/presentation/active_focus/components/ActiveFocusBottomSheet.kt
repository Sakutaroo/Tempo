package ca.ulaval.ima.mp.focus.presentation.active_focus.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoBottomSheet

@Composable
fun ActiveFocusBottomSheet(
    onDismissRequest: () -> Unit,
    onEndSessionClick: () -> Unit,
    showBottomSheet: Boolean,
    endSessionLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if (showBottomSheet) {
        TempoBottomSheet(
            onDismissRequest = {
                onDismissRequest()
            },
            onEndSessionClick = {
                onEndSessionClick()
            },
            endSessionLoading = endSessionLoading,
            modifier = modifier
        )
    }
}
