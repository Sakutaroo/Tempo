package ca.ulaval.ima.mp.auth.presentation.register

import ca.ulaval.ima.mp.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegisterSuccess : RegisterEvent
    data class Error(val error: UiText) : RegisterEvent
}
