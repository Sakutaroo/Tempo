@file:OptIn(ExperimentalCoroutinesApi::class)

package ca.ulaval.ima.mp.focus.domain.motion

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration

class MotionTracker(
    private val motionManager: MotionDetectionManager,
    scope: CoroutineScope
) {
    private val _elapsedTime = MutableStateFlow(Duration.ZERO)
    val elapsedTime = _elapsedTime.asStateFlow()

    private val _motionCount = MutableStateFlow(0)
    val motionCount = _motionCount.asStateFlow()

    private val _motionState = MutableStateFlow(MotionState.STOPPED)
    val motionState: StateFlow<MotionState> = _motionState.asStateFlow()

    init {
        motionManager.motionState
            .flatMapLatest { motionState ->
                if (motionState == MotionState.ACTIVE) {
                    Timer.timeAndEmit()
                } else {
                    flowOf()
                }
            }
            .onEach {
                _elapsedTime.value += it
            }
            .launchIn(scope)

        motionManager.motionEvents
            .onEach {
                _motionCount.value += 1
            }
            .launchIn(scope)

        motionManager.motionState
            .onEach {
                _motionState.value = it
            }
            .launchIn(scope)
    }


    fun start() {
        motionManager.start()
    }

    fun pause() {
        motionManager.pause()
    }

    fun resume() {
        motionManager.resume()
    }

    fun stop() {
        _elapsedTime.value = Duration.ZERO
        _motionCount.value = 0
        _motionState.value = MotionState.STOPPED
        motionManager.stop()
    }
}
