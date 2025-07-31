package ca.ulaval.ima.mp.focus.presentation.active_focus

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ca.ulaval.ima.mp.focus.presentation.active_focus.utils.hasActivityPermission
import ca.ulaval.ima.mp.focus.presentation.active_focus.utils.hasNotificationPermission
import ca.ulaval.ima.mp.focus.presentation.active_focus.utils.shouldShowActivityPermissionRationale
import ca.ulaval.ima.mp.focus.presentation.active_focus.utils.shouldShowNotificationPermissionRationale

@Composable
fun PermissionManager(
    permissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    context: Context,
    onAction: (ActiveFocusAction) -> Unit
) {
    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showActivityRationale = activity.shouldShowActivityPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveFocusAction.SubmitActivityPermissionInfo(
                acceptedActivityPermission = context.hasActivityPermission(),
                showActivityPermissionRationale = showActivityRationale
            )
        )
        onAction(
            ActiveFocusAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationPermissionRationale = showNotificationRationale
            )
        )

        if (!showActivityRationale && !showNotificationRationale) {
            permissionLauncher.requestTempoPermissions(context)
        }
    }
}

fun ActivityResultLauncher<Array<String>>.requestTempoPermissions(
    context: Context
) {
    val hasActivityPermission = context.hasActivityPermission()
    val hasNotificationPermission = context.hasNotificationPermission()
    val activityPermission = if (Build.VERSION.SDK_INT >= 29) {
        arrayOf(Manifest.permission.ACTIVITY_RECOGNITION)
    } else arrayOf()
    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else arrayOf()

    when {
        !hasActivityPermission && !hasNotificationPermission -> launch(activityPermission + notificationPermission)
        !hasActivityPermission -> launch(activityPermission)
        !hasNotificationPermission -> launch(notificationPermission)
    }
}
