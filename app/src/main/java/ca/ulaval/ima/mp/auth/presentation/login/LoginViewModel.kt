package ca.ulaval.ima.mp.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.ulaval.ima.mp.core.domain.supabase.SupaRepository
import ca.ulaval.ima.mp.core.domain.utils.Result
import ca.ulaval.ima.mp.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val supaRepository: SupaRepository
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeFormValidity()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = LoginState()
        )

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnTogglePasswordVisibilityClick -> togglePasswordVisibility()
            LoginAction.OnLoginClick -> login()
            else -> Unit
        }
    }

    private fun togglePasswordVisibility() {
        _state.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    private fun login() {
        viewModelScope.launch {
            val result = supaRepository.login(
                email = _state.value.email.text.toString().trim(),
                password = _state.value.password.text.toString().trim()
            )
            when (result) {
                is Result.Error -> {
                    _events.send(LoginEvent.Error(result.error.asUiText()))
                }

                is Result.Success -> {
                    _events.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }

    private fun observeFormValidity() {
        val emailFlow = snapshotFlow {
            _state.value.email.text.toString().trim()
        }
        val passwordFlow = snapshotFlow {
            _state.value.password.text.toString()
        }

        combine(emailFlow, passwordFlow) { email, password ->
            email to password
        }
            .distinctUntilChanged()
            .onEach { (email, password) ->
                val isEmailValid = email.isNotEmpty()
                val isPasswordValid = password.isNotEmpty()
                _state.update {
                    it.copy(
                        canLogin = isEmailValid && isPasswordValid
                    )
                }
            }
            .launchIn(viewModelScope)

    }
}
