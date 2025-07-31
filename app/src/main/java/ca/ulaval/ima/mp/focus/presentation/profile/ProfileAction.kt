package ca.ulaval.ima.mp.focus.presentation.profile

import ca.ulaval.ima.mp.focus.domain.Session

sealed interface ProfileAction {
    data object OnLogoutClick : ProfileAction
    data class OnSessionClick(val session: Session) : ProfileAction
}
