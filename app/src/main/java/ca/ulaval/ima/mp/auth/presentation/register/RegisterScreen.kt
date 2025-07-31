package ca.ulaval.ima.mp.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.auth.presentation.register.components.RegisterButton
import ca.ulaval.ima.mp.auth.presentation.register.components.RegisterClickableText
import ca.ulaval.ima.mp.auth.presentation.register.components.RegisterFields
import ca.ulaval.ima.mp.auth.presentation.register.components.RegisterTitle
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            RegisterEvent.RegisterSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    context.getString(R.string.registration_successful),
                    Toast.LENGTH_LONG
                ).show()
                onRegisterSuccess()
            }
        }
    }

    RegisterScreen(
        state = state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnLoginClick -> onLoginClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        RegisterTitle()
        RegisterFields(
            emailState = state.email,
            isEmailValid = state.isEmailValid,
            passwordState = state.password,
            passwordValidationState = state.passwordValidationState,
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = {
                onAction(RegisterAction.OnTogglePasswordVisibilityClick)
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RegisterButton(
                isRegistering = state.isRegistering,
                canRegister = state.canRegister,
                onRegisterClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )
            RegisterClickableText(
                onLoginClick = {
                    onAction(RegisterAction.OnLoginClick)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TempoTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}
