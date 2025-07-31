package ca.ulaval.ima.mp.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import ca.ulaval.ima.mp.auth.domain.PasswordValidationState

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val canRegister: Boolean = false,
    val isRegistering: Boolean = false
)
