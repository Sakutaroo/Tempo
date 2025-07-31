package ca.ulaval.ima.mp.auth.presentation.login

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
import ca.ulaval.ima.mp.auth.presentation.login.components.LoginButton
import ca.ulaval.ima.mp.auth.presentation.login.components.LoginClickableText
import ca.ulaval.ima.mp.auth.presentation.login.components.LoginFields
import ca.ulaval.ima.mp.auth.presentation.login.components.LoginTitle
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    context.getString(R.string.you_are_logged_in),
                    Toast.LENGTH_LONG
                ).show()
                onLoginSuccess()
            }
        }
    }

    LoginScreen(
        state = state,
        onAction = { action ->
            when (action) {
                LoginAction.OnRegisterClick -> onRegisterClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        LoginTitle()
        LoginFields(
            emailState = state.email,
            passwordState = state.password,
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = {
                onAction(LoginAction.OnTogglePasswordVisibilityClick)
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LoginButton(
                isLoggingIn = state.isLoggingIn,
                canLogin = state.canLogin,
                onLoginClick = {
                    onAction(LoginAction.OnLoginClick)
                }
            )
            LoginClickableText(
                onRegisterClick = {
                    onAction(LoginAction.OnRegisterClick)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TempoTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}
