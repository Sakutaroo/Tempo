package ca.ulaval.ima.mp.auth.presentation.register.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoActionButton

@Composable
fun RegisterButton(
    isRegistering: Boolean,
    canRegister: Boolean,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TempoActionButton(
        text = stringResource(R.string.register),
        isLoading = isRegistering,
        onClick = {
            onRegisterClick()
        },
        modifier = modifier,
        enabled = canRegister && !isRegistering
    )
}
