package ca.ulaval.ima.mp.focus.presentation.focus_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.ulaval.ima.mp.core.domain.supabase.SupaRepository
import ca.ulaval.ima.mp.core.domain.supabase.mappers.toDurationSeconds
import ca.ulaval.ima.mp.core.domain.supabase.mappers.toSession
import ca.ulaval.ima.mp.core.domain.utils.Result
import ca.ulaval.ima.mp.focus.domain.mappers.toDurationUi
import ca.ulaval.ima.mp.focus.presentation.mappers.toSessionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FocusViewModel(
    private val supaRepository: SupaRepository
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(FocusState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                getSessions()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FocusState()
        )

    fun onAction(action: FocusAction) {
        when (action) {
            else -> Unit
        }
    }

    private fun getSessions() {
        _state.update {
            it.copy(
                areSessionsLoading = true
            )
        }

        viewModelScope.launch {
            val result = supaRepository.getSessions()

            when (result) {
                is Result.Error -> Unit
                is Result.Success -> {
                    val lastItems = result.data
                        .sortedByDescending { it.createdAt }
                        .take(5)
                    _state.update {
                        it.copy(
                            sessions = lastItems
                                .map { session ->
                                    session.toSession().toSessionUi()
                                },
                            focusScoreAvg = if (lastItems.isNotEmpty()) {
                                val focusScore = lastItems
                                    .sumOf { it.focusScore } / lastItems.size
                                focusScore.toString()
                            } else {
                                "0"
                            },
                            focusedTimeAvg = if (lastItems.isNotEmpty()) {
                                val focusedTime = lastItems
                                    .sumOf { it.durationInSeconds } / lastItems.size
                                focusedTime.toDurationSeconds().toDurationUi()
                            } else {
                                "-"
                            },
                            bestFocusScore = (lastItems
                                .maxOfOrNull { it.focusScore } ?: "-").toString(),
                            bestTime = if (lastItems.isNotEmpty()) {
                                val bestTime = lastItems
                                    .maxOf { it.durationInSeconds }
                                bestTime.toDurationSeconds().toDurationUi()
                            } else {
                                "-"
                            },
                            areSessionsLoading = false
                        )
                    }
                }
            }
        }
    }
}
