package ca.ulaval.ima.mp.focus.presentation.active_focus.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.focus.domain.motion.MotionState

@Composable
fun ActiveFocusActions(
    onToggleClick: (() -> Unit),
    onStopClick: () -> Unit,
    motionState: MotionState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onToggleClick()
                }
        ) {
            Icon(
                imageVector = when (motionState) {
                    MotionState.ACTIVE -> Icons.Outlined.Pause
                    else -> Icons.Outlined.PlayArrow

                },
                contentDescription = when (motionState) {
                    MotionState.ACTIVE -> stringResource(R.string.pause)
                    else -> stringResource(R.string.resume)
                },
                tint = Gray950,
                modifier = Modifier
                    .size(40.dp)
            )
        }
        Spacer(modifier = Modifier.width(48.dp))
        Box(
            modifier = Modifier
                .clickable {
                    onStopClick()
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.Stop,
                contentDescription = stringResource(R.string.stop),
                tint = Gray950,
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
}
