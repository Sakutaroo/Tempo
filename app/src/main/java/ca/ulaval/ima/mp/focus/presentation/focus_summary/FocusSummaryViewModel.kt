package ca.ulaval.ima.mp.focus.presentation.focus_summary

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.ulaval.ima.mp.core.domain.supabase.SupaRepository
import ca.ulaval.ima.mp.core.domain.supabase.mappers.toSessionInsertRequest
import ca.ulaval.ima.mp.core.domain.supabase.mappers.toSessionUpdateRequest
import ca.ulaval.ima.mp.core.domain.utils.Result
import ca.ulaval.ima.mp.core.presentation.ui.asUiText
import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.presentation.mappers.toSessionUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FocusSummaryViewModel(
    private val session: Session,
    private val isEditing: Boolean,
    private val supaRepository: SupaRepository
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(FocusSummaryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadSessionSummary(session)
                _state.update {
                    it.copy(
                        isEditing = isEditing
                    )
                }
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FocusSummaryState()
        )

    private val _events = Channel<FocusSummaryEvent>()
    val events = _events.receiveAsFlow()

    private fun loadSessionSummary(session: Session) {
        _state.update {
            it.copy(
                session = session.toSessionUi(),
                descriptionTextField = TextFieldState(session.description ?: "")
            )
        }
    }

    fun onAction(action: FocusSummaryAction) {
        when (action) {
            FocusSummaryAction.OnValidClick -> saveSession()
            FocusSummaryAction.OnDeleteClick -> deleteSession()
            else -> Unit
        }
    }

    private fun deleteSession() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    deleteLoading = true
                )
            }

            val result = supaRepository.deleteSession(
                sessionId = _state.value.session.id.toString()
            )
            when (result) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            deleteLoading = false
                        )
                    }
                    _events.send(FocusSummaryEvent.SessionSavedError(result.error.asUiText()))
                }

                is Result.Success -> {
                    _events.send(FocusSummaryEvent.SessionDeleted)
                }
            }
        }
    }

    private fun saveSession() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    validLoading = true
                )
            }

            val description = getTrimDescription()
            val result = if (!isEditing) {
                supaRepository.saveSession(
                    session = Session(
                        id = null,
                        durationInSeconds = session.durationInSeconds,
                        description = description,
                        focusScore = _state.value.session.focusScore.toInt()
                    ).toSessionInsertRequest()
                )
            } else {
                supaRepository.updateSession(
                    session = Session(
                        id = session.id,
                        durationInSeconds = session.durationInSeconds,
                        description = description,
                        focusScore = _state.value.session.focusScore.toInt()
                    ).toSessionUpdateRequest()
                )
            }

            when (result) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            validLoading = false
                        )
                    }
                    _events.send(FocusSummaryEvent.SessionSavedError(result.error.asUiText()))
                }

                is Result.Success -> _events.send(FocusSummaryEvent.SessionSaved)
            }
        }
    }

    private fun getTrimDescription(): String {
        return _state.value.descriptionTextField.text.toString().trim()
            .takeIf { it.isNotEmpty() } ?: "Work session"
    }
}
