package ca.ulaval.ima.mp.focus.presentation.focus_overview

import ca.ulaval.ima.mp.focus.presentation.models.SessionUi

data class FocusState(
    val sessions: List<SessionUi> = emptyList(),
    val areSessionsLoading: Boolean = false,
    val focusScoreAvg: String = "0",
    val focusedTimeAvg: String = "-",
    val bestFocusScore: String = "-",
    val bestTime: String = "-"
)
