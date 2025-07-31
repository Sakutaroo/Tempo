package ca.ulaval.ima.mp.focus.presentation.active_focus

sealed interface ActiveFocusAction {
    data object OnStopFocus : ActiveFocusAction
    data object OnPauseFocus : ActiveFocusAction
    data object OnResumeFocus : ActiveFocusAction
    data object ToggleBottomSheet : ActiveFocusAction
    data object OnEndSessionClick : ActiveFocusAction
    data class SubmitActivityPermissionInfo(
        val acceptedActivityPermission: Boolean,
        val showActivityPermissionRationale: Boolean
    ) : ActiveFocusAction
    data class SubmitNotificationPermissionInfo(
        val acceptedNotificationPermission: Boolean,
        val showNotificationPermissionRationale: Boolean
    ) : ActiveFocusAction
    data object DismissRationaleDialog : ActiveFocusAction
}
