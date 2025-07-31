package ca.ulaval.ima.mp.app

import ca.ulaval.ima.mp.focus.domain.motion.MotionState

data class MainState(
    val motionState: MotionState = MotionState.STOPPED,
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = true
)
