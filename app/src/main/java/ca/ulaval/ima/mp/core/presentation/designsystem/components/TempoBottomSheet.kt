@file:OptIn(ExperimentalMaterial3Api::class)

package ca.ulaval.ima.mp.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray200
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.utils.BottomSheet
import ca.ulaval.ima.mp.core.presentation.designsystem.components.utils.EndSessionSheet

@Composable
fun TempoBottomSheet(
    onDismissRequest: () -> Unit,
    onEndSessionClick: () -> Unit,
    endSessionLoading: Boolean,
    modifier: Modifier = Modifier
) {
    BottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        containerColor = Gray200
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EndSessionSheet(
                onEndClick = onEndSessionClick,
                onDismissRequest = onDismissRequest,
                endSessionLoading = endSessionLoading
            )
        }
    }
}

@Preview
@Composable
private fun TempoBottomSheetPreview() {
    TempoTheme {
        TempoBottomSheet(
            onDismissRequest = {},
            onEndSessionClick = {},
            endSessionLoading = false,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
