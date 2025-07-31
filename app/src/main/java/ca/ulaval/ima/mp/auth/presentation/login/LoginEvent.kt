package ca.ulaval.ima.mp.auth.presentation.login

import ca.ulaval.ima.mp.core.presentation.ui.UiText

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
    data class Error(val error: UiText) : LoginEvent
}
