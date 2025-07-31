package ca.ulaval.ima.mp.auth.presentation.login.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoActionButton

@Composable
fun LoginButton(
    isLoggingIn: Boolean,
    canLogin: Boolean,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TempoActionButton(
        text = stringResource(R.string.log_in),
        isLoading = isLoggingIn,
        onClick = {
            onLoginClick()
        },
        modifier = modifier,
        enabled = canLogin && !isLoggingIn
    )
}
