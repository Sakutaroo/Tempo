package ca.ulaval.ima.mp.auth.presentation.register

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.auth.domain.UserDataValidator
import ca.ulaval.ima.mp.core.domain.supabase.SupaRepository
import ca.ulaval.ima.mp.core.domain.utils.DataError
import ca.ulaval.ima.mp.core.domain.utils.Result
import ca.ulaval.ima.mp.core.presentation.ui.UiText
import ca.ulaval.ima.mp.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val supaRepository: SupaRepository
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeEmailValidity()
                observePasswordValidity()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = RegisterState()
        )

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> togglePasswordVisibility()
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

    private fun register() {
        viewModelScope.launch {
            val result = supaRepository.register(
                email = _state.value.email.text.toString().trim(),
                password = _state.value.password.text.toString()
            )
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        _events.send(
                            RegisterEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            )
                        )
                    } else {
                        _events.send(RegisterEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _events.send(RegisterEvent.RegisterSuccess)
                }
            }
        }
    }

    private fun observeEmailValidity() {
        val emailFlow = snapshotFlow {
            _state.value.email.text.toString().trim()
        }

        emailFlow
            .distinctUntilChanged()
            .onEach { email ->
                val isEmailValid = userDataValidator.isValidEmail(email)
                _state.update {
                    it.copy(
                        isEmailValid = isEmailValid,
                        canRegister = isEmailValid && _state.value.passwordValidationState.isValidPassword && !_state.value.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observePasswordValidity() {
        val passwordFlow = snapshotFlow {
            _state.value.password.text.toString()
        }

        passwordFlow
            .distinctUntilChanged()
            .onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password)
                _state.update {
                    it.copy(
                        passwordValidationState = passwordValidationState,
                        canRegister = _state.value.isEmailValid && passwordValidationState.isValidPassword && !_state.value.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
