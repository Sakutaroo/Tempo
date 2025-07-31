package ca.ulaval.ima.mp.focus.presentation.active_focus

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoSphere
import ca.ulaval.ima.mp.core.presentation.ui.ObserveAsEvents
import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.domain.mappers.toDurationUi
import ca.ulaval.ima.mp.focus.domain.motion.MotionState
import ca.ulaval.ima.mp.focus.presentation.active_focus.components.ActiveFocusActions
import ca.ulaval.ima.mp.focus.presentation.active_focus.components.ActiveFocusBottomSheet
import ca.ulaval.ima.mp.focus.presentation.active_focus.components.ActiveFocusDuration
import ca.ulaval.ima.mp.focus.presentation.active_focus.components.ActiveFocusPermissionDialog
import ca.ulaval.ima.mp.focus.presentation.active_focus.components.ActiveFocusQuote
import ca.ulaval.ima.mp.focus.presentation.active_focus.components.ActiveFocusTopBar
import ca.ulaval.ima.mp.focus.presentation.active_focus.utils.shouldShowActivityPermissionRationale
import ca.ulaval.ima.mp.focus.presentation.active_focus.utils.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveFocusScreenRoot(
    onStopFocus: (Session) -> Unit,
    viewModel: ActiveFocusViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ActiveFocusEvent.SessionStopped -> {
                onStopFocus(
                    Session(
                        id = null,
                        durationInSeconds = state.elapsedTime,
                        description = null,
                        focusScore = state.focusScore
                    )
                )
            }
        }
    }

    ActiveFocusScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ActiveFocusScreen(
    state: ActiveFocusState,
    onAction: (ActiveFocusAction) -> Unit,
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasActivityPermission = if (Build.VERSION.SDK_INT >= 29) {
            perms[Manifest.permission.ACTIVITY_RECOGNITION] == true
        } else true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true
        val activity = context as ComponentActivity
        val showActivityRationale = activity.shouldShowActivityPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveFocusAction.SubmitActivityPermissionInfo(
                acceptedActivityPermission = hasActivityPermission,
                showActivityPermissionRationale = showActivityRationale
            )
        )
        onAction(
            ActiveFocusAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationPermissionRationale = showNotificationRationale
            )
        )
    }

    PermissionManager(
        permissionLauncher = permissionLauncher,
        context = context,
        onAction = onAction
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Gray50)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        ActiveFocusBottomSheet(
            onDismissRequest = {
                onAction(ActiveFocusAction.ToggleBottomSheet)
            },
            onEndSessionClick = {
                onAction(ActiveFocusAction.OnEndSessionClick)
            },
            showBottomSheet = state.showBottomSheet,
            endSessionLoading = state.endSessionLoading
        )
        ActiveFocusTopBar(
            onStopClick = {
                onAction(ActiveFocusAction.OnStopFocus)
            }
        )
        ActiveFocusQuote()
        ActiveFocusDuration(
            duration = state.elapsedTime.toDurationUi()
        )
        TempoSphere()
        ActiveFocusActions(
            onToggleClick = {
                onAction(
                    when (state.motionState) {
                        MotionState.ACTIVE -> ActiveFocusAction.OnPauseFocus
                        MotionState.PAUSED -> ActiveFocusAction.OnResumeFocus
                        else -> ActiveFocusAction.OnStopFocus
                    }
                )
            },
            onStopClick = {
                onAction(ActiveFocusAction.OnStopFocus)
            },
            motionState = state.motionState
        )
    }

    if (state.showActivityRationale || state.showNotificationRationale) {
        ActiveFocusPermissionDialog(
            showActivityRationale = state.showActivityRationale,
            showNotificationRationale = state.showNotificationRationale,
            requestTempoPermissions = {
                permissionLauncher.requestTempoPermissions(context)
            },
            onDismissRationaleDialog = {
                onAction(ActiveFocusAction.DismissRationaleDialog)
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TempoTheme {
        ActiveFocusScreen(
            state = ActiveFocusState(),
            onAction = {}
        )
    }
}
