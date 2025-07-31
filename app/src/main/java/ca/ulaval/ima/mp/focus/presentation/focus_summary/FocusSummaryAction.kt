package ca.ulaval.ima.mp.focus.presentation.focus_summary

sealed interface FocusSummaryAction {
    data object OnValidClick : FocusSummaryAction
    data object OnDeleteClick : FocusSummaryAction
    data object OnBackClick : FocusSummaryAction
}
