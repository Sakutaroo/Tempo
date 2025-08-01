package ca.ulaval.ima.mp.focus.presentation.active_focus.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

fun ComponentActivity.shouldShowActivityPermissionRationale(): Boolean {
    return Build.VERSION.SDK_INT >= 29 && shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION)
}

fun ComponentActivity.shouldShowNotificationPermissionRationale() : Boolean {
    return Build.VERSION.SDK_INT >= 33 && shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
}

private fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasActivityPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= 29) {
        hasPermission(Manifest.permission.ACTIVITY_RECOGNITION)
    } else true
}

fun Context.hasNotificationPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= 33) {
        hasPermission(Manifest.permission.POST_NOTIFICATIONS)
    } else true
}
