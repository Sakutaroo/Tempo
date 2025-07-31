package ca.ulaval.ima.mp.auth.presentation.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.auth.domain.PasswordValidationState

@Composable
fun RegisterFields(
    emailState: TextFieldState,
    isEmailValid: Boolean,
    passwordState: TextFieldState,
    passwordValidationState: PasswordValidationState,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        RegisterEmailField(
            emailState = emailState,
            isEmailValid = isEmailValid
        )
        RegisterPasswordField(
            passwordState = passwordState,
            passwordValidationState = passwordValidationState,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility
        )
    }
}
