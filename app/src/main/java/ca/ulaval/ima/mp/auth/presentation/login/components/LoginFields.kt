package ca.ulaval.ima.mp.auth.presentation.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoPasswordField
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoTextField

@Composable
fun LoginFields(
    emailState: TextFieldState,
    passwordState: TextFieldState,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TempoTextField(
            state = emailState,
            hint = stringResource(R.string.tempo_mail),
            title = stringResource(R.string.email),
            keyboardType = KeyboardType.Email
        )
        TempoPasswordField(
            state = passwordState,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            hint = stringResource(R.string.password),
            title = stringResource(R.string.password)
        )
    }
}

@Preview
@Composable
private fun LoginFieldsPreview() {
    TempoTheme {
        LoginFields(
            emailState = TextFieldState(""),
            passwordState = TextFieldState(""),
            isPasswordVisible = false,
            onTogglePasswordVisibility = { }
        )
    }
}
