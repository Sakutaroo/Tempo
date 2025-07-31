package ca.ulaval.ima.mp.focus.presentation.active_focus.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoActionButton
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoDialog

@Composable
fun ActiveFocusPermissionDialog(
    showActivityRationale: Boolean,
    showNotificationRationale: Boolean,
    requestTempoPermissions: () -> Unit,
    onDismissRationaleDialog: () -> Unit
) {
    TempoDialog(
        title = stringResource(R.string.permission_required),
        onDismiss = { /* Normal dismissing not allowed for permissions */ },
        description = when {
            showActivityRationale && showNotificationRationale -> stringResource(R.string.activity_notification_rationale)
            showActivityRationale -> stringResource(R.string.activity_rationale)
            else -> stringResource(R.string.notification_rationale)
        },
        primaryButton = {
            TempoActionButton(
                text = stringResource(R.string.okay),
                isLoading = false,
                onClick = {
                    onDismissRationaleDialog()
                    requestTempoPermissions()
                }
            )
        }
    )
}
