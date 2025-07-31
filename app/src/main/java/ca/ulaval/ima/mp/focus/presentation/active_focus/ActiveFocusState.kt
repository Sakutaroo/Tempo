package ca.ulaval.ima.mp.focus.presentation.active_focus

import ca.ulaval.ima.mp.focus.domain.motion.MotionState
import kotlin.time.Duration

data class ActiveFocusState(
    val elapsedTime: Duration = Duration.ZERO,
    val showBottomSheet: Boolean = false,
    val endSessionLoading: Boolean = false,
    val motionCount: Int = 0,
    val focusScore: Int = 0,
    val motionState: MotionState = MotionState.STOPPED,
    val showNotificationRationale: Boolean = false,
    val showActivityRationale: Boolean = false
)
