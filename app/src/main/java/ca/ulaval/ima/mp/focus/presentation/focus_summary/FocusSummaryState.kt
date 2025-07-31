package ca.ulaval.ima.mp.focus.presentation.focus_summary

import androidx.compose.foundation.text.input.TextFieldState
import ca.ulaval.ima.mp.focus.presentation.models.SessionUi

data class FocusSummaryState(
    val session: SessionUi = SessionUi(),
    val descriptionTextField: TextFieldState = TextFieldState(""),
    val validLoading: Boolean = false,
    val isEditing: Boolean = false,
    val deleteLoading: Boolean = false
)
