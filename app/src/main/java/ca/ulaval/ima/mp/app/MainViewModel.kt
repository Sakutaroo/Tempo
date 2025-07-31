package ca.ulaval.ima.mp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.ulaval.ima.mp.core.domain.supabase.SupaRepository
import ca.ulaval.ima.mp.core.domain.utils.Result
import ca.ulaval.ima.mp.focus.domain.motion.MotionState
import ca.ulaval.ima.mp.focus.domain.motion.MotionTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val supaRepository: SupaRepository,
    private val motionTracker: MotionTracker
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isCheckingAuth = true
                    )
                }

                observeMotionState()
                checkConnectionStatus()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )

    private fun checkConnectionStatus() {
        viewModelScope.launch {
            val isLoggedIn = if (_state.value.motionState == MotionState.ACTIVE) {
                Result.Success(true)
            } else {
                supaRepository.tryRestoreSession()
            }

            when (isLoggedIn) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoggedIn = isLoggedIn.data,
                            isCheckingAuth = false
                        )
                    }
                }
                else -> Unit
            }
        }
    }

    private fun observeMotionState() {
        motionTracker.motionState
            .onEach { motionState ->
                _state.update {
                    it.copy(
                        motionState = motionState,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
