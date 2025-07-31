package ca.ulaval.ima.mp.focus.presentation.active_focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.ulaval.ima.mp.focus.domain.motion.MotionState
import ca.ulaval.ima.mp.focus.domain.motion.MotionTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class ActiveFocusViewModel(
    private val motionTracker: MotionTracker
) : ViewModel() {
    private var hasLoadedInitialData = false
    private val hasActivityPermission = MutableStateFlow(false)

    private val _state = MutableStateFlow(ActiveFocusState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                startFocus()
                observeMotionTracker()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ActiveFocusState()
        )

    private val _events = Channel<ActiveFocusEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ActiveFocusAction) {
        when (action) {
            ActiveFocusAction.OnPauseFocus -> pauseFocus()
            ActiveFocusAction.OnResumeFocus -> resumeFocus()
            ActiveFocusAction.OnStopFocus -> stopFocus()
            ActiveFocusAction.ToggleBottomSheet -> toggleBottomSheet()
            ActiveFocusAction.OnEndSessionClick -> endSession()
            ActiveFocusAction.DismissRationaleDialog -> dismissRationaleDialog()
            is ActiveFocusAction.SubmitActivityPermissionInfo -> {
                hasActivityPermission.value = action.acceptedActivityPermission
                _state.update {
                    it.copy(
                        showActivityRationale = action.showActivityPermissionRationale
                    )
                }
            }
            is ActiveFocusAction.SubmitNotificationPermissionInfo -> {
                _state.update {
                    it.copy(
                        showNotificationRationale = action.showNotificationPermissionRationale
                    )
                }
            }
        }
    }

    private fun observeMotionTracker() {
        motionTracker.elapsedTime
            .onEach { elapsedTime ->
                _state.update {
                    it.copy(
                        elapsedTime = elapsedTime
                    )
                }
            }
            .launchIn(viewModelScope)

        motionTracker.motionCount
            .onEach { count ->
                _state.update {
                    it.copy(
                        motionCount = count
                    )
                }
            }
            .launchIn(viewModelScope)

        motionTracker.motionState
            .onEach { motionState ->
                setMotionState(motionState)
            }
            .launchIn(viewModelScope)
    }

    private fun endSession() {
        _state.update {
            it.copy(
                endSessionLoading = true,
                focusScore = calculateFocusScore()
            )
        }

        viewModelScope.launch {
            yield()
            _events.send(ActiveFocusEvent.SessionStopped)
            motionTracker.stop()
        }
    }

    private fun calculateFocusScore(): Int {
        val elapsedTime = _state.value.elapsedTime
        val motionCount = _state.value.motionCount
        val minutes = elapsedTime.inWholeSeconds.coerceAtLeast(1) / 60.0
        val shakeRate = motionCount / minutes

        return (100 - shakeRate * 10).toInt().coerceIn(0, 100)
    }

    private fun toggleBottomSheet() {
        _state.update {
            it.copy(
                showBottomSheet = !_state.value.showBottomSheet
            )
        }
    }

    private fun setMotionState(newMotionState: MotionState) {
        _state.update {
            it.copy(
                motionState = newMotionState
            )
        }
    }

    private fun stopFocus() {
        _state.update {
            it.copy(
                showBottomSheet = !_state.value.showBottomSheet
            )
        }
        motionTracker.pause()
    }

    private fun startFocus() {
        motionTracker.start()
        hasActivityPermission
            .onEach { hasPermission ->
                if (!hasPermission) {
                    pauseFocus()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun pauseFocus() {
        motionTracker.pause()
    }

    private fun resumeFocus() {
        motionTracker.resume()
    }

    private fun dismissRationaleDialog() {
        _state.update {
            it.copy(
                showActivityRationale = false,
                showNotificationRationale = false
            )
        }
    }
}
