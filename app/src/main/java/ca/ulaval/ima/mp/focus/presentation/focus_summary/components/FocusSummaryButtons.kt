package ca.ulaval.ima.mp.focus.presentation.focus_summary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FocusSummaryButtons(
    validLoading: Boolean,
    onValidClick: () -> Unit,
    deleteLoading: Boolean,
    onDeleteClick: () -> Unit,
    isEditing: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FocusSummaryValid(
            validLoading = validLoading,
            onValidClick = onValidClick
        )
        if (isEditing) {
            FocusSummaryDelete(
                deleteLoading = deleteLoading,
                onDeleteClick = onDeleteClick
            )
        }
    }
}
