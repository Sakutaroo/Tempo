package ca.ulaval.ima.mp.focus.presentation.focus_overview

import ca.ulaval.ima.mp.focus.domain.Session

sealed interface FocusAction {
    data class OnSessionClick(val session: Session) : FocusAction
    data object OnStartSessionClick : FocusAction
}
