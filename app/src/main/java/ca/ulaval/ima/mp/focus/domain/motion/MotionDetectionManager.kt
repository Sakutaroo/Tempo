package ca.ulaval.ima.mp.focus.domain.motion

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MotionDetectionManager {
    fun start()
    fun pause()
    fun resume()
    fun stop()
    fun isActive(): Boolean
    fun getState(): MotionState
    val motionEvents: SharedFlow<Unit>
    val motionState: StateFlow<MotionState>
}
