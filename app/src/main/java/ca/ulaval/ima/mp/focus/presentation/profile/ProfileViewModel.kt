package ca.ulaval.ima.mp.focus.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.ulaval.ima.mp.core.domain.supabase.SupaRepository
import ca.ulaval.ima.mp.core.domain.supabase.mappers.toDurationSeconds
import ca.ulaval.ima.mp.core.domain.supabase.mappers.toSession
import ca.ulaval.ima.mp.core.domain.utils.Result
import ca.ulaval.ima.mp.focus.domain.mappers.toDurationUi
import ca.ulaval.ima.mp.focus.presentation.mappers.toSessionUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val supaRepository: SupaRepository
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProfileState())
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
            initialValue = ProfileState()
        )

    private val _events = Channel<ProfileEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogoutClick -> logout()
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
                    _state.update {
                        it.copy(
                            sessions = result.data
                                .sortedByDescending { sessionResponse ->
                                    sessionResponse.createdAt
                                }
                                .map { session ->
                                    session.toSession().toSessionUi()
                                },
                            focusScoreAvg = if (result.data.isNotEmpty()) {
                                val focusScore = result.data
                                    .sumOf { it.focusScore } / result.data.size
                                focusScore.toString()
                            } else {
                                "0"
                            },
                            focusedTimeAvg = if (result.data.isNotEmpty()) {
                                val focusedTime = result.data
                                    .sumOf { it.durationInSeconds } / result.data.size
                                focusedTime.toDurationSeconds().toDurationUi()
                            } else {
                                "-"
                            },
                            bestFocusScore = (result.data
                                .maxOfOrNull { it.focusScore } ?: "-").toString(),
                            bestTime = if (result.data.isNotEmpty()) {
                                val bestTime = result.data
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

    private fun logout() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    logoutLoading = true
                )
            }
            supaRepository.logout()
            _state.update {
                it.copy(
                    logoutLoading = false
                )
            }
            _events.send(ProfileEvent.UserLogout)
        }
    }
}
