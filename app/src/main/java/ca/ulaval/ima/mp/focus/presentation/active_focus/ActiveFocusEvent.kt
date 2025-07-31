package ca.ulaval.ima.mp.focus.presentation.active_focus

sealed interface ActiveFocusEvent {
    data object SessionStopped : ActiveFocusEvent
}
