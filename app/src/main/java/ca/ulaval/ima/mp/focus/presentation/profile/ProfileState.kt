package ca.ulaval.ima.mp.focus.presentation.profile

import ca.ulaval.ima.mp.focus.presentation.models.SessionUi

data class ProfileState(
    val sessions: List<SessionUi> = emptyList(),
    val focusScoreAvg: String = "0",
    val focusedTimeAvg: String = "-",
    val bestFocusScore: String = "-",
    val bestTime: String = "-",
    val areSessionsLoading: Boolean = false,
    val logoutLoading: Boolean = false
)
