package ca.ulaval.ima.mp.auth.presentation.intro.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoActionButton

@Composable
fun AuthButtons(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TempoActionButton(
            text = stringResource(R.string.log_in),
            isLoading = false,
            onClick = {
                onLoginClick()
            },
            containerColor = Gray50,
            contentColor = Gray950,
            borderColor = Gray950
        )
        TempoActionButton(
            text = stringResource(R.string.register),
            isLoading = false,
            onClick = {
                onRegisterClick()
            }
        )
    }
}

@Preview
@Composable
private fun AuthButtonsPreview() {
    TempoTheme {
        AuthButtons(
            onLoginClick = { },
            onRegisterClick = { }
        )
    }
}
