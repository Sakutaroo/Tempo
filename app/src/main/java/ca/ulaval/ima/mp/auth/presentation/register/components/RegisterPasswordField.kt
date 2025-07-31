package ca.ulaval.ima.mp.auth.presentation.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.auth.domain.PasswordValidationState
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoPasswordField

@Composable
fun RegisterPasswordField(
    passwordState: TextFieldState,
    passwordValidationState: PasswordValidationState,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TempoPasswordField(
            state = passwordState,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            hint = stringResource(R.string.password),
            title = stringResource(R.string.password)
        )
        RegisterPasswordRequirements(
            passwordValidationState = passwordValidationState
        )
    }
}
