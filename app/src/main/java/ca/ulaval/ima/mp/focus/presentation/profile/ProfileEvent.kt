package ca.ulaval.ima.mp.focus.presentation.profile

sealed interface ProfileEvent {
    data object UserLogout : ProfileEvent
}
