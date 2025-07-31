package ca.ulaval.ima.mp.focus.presentation.focus_summary

import ca.ulaval.ima.mp.core.presentation.ui.UiText

sealed interface FocusSummaryEvent {
    data object SessionSaved : FocusSummaryEvent
    data object SessionDeleted : FocusSummaryEvent
    data class SessionSavedError(val error: UiText) : FocusSummaryEvent
}
