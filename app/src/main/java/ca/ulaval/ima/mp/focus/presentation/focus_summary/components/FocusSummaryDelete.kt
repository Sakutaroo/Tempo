package ca.ulaval.ima.mp.focus.presentation.focus_summary.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.RedContainer
import ca.ulaval.ima.mp.core.presentation.designsystem.RedContent
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoActionButton

@Composable
fun FocusSummaryDelete(
    deleteLoading: Boolean,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TempoActionButton(
            text = stringResource(R.string.delete),
            isLoading = deleteLoading,
            onClick = onDeleteClick,
            enabled = !deleteLoading,
            containerColor = RedContainer,
            contentColor = RedContent
        )
    }
}
