package com.irisvpn.android.androidSpecific

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import dev.dev7.lib.v2ray.V2rayController

fun isPreparedForConnection(context: Context): Boolean {
    return V2rayController.isPreparedForConnection(context)
}

@SuppressLint("InlinedApi")
fun prepareForConnection(
    activity: ComponentActivity,
    activityResultLauncher: ActivityResultLauncher<Intent>,
) {
    val vpnServicePrepareIntent = VpnService.prepare(activity)
    if (vpnServicePrepareIntent != null) {
        activityResultLauncher.launch(vpnServicePrepareIntent)
    }
}

fun requestNotification(activity: ComponentActivity, permissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>) {
    if (!hasNotificationPermission(activity)) {
        permissionResultLauncher.launch(POST_NOTIFICATIONS)
        return
    }
}

fun hasNotificationPermission(activity: ComponentActivity): Boolean {
    if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)) {
        return true
    }
    return ContextCompat.checkSelfPermission(
        activity, POST_NOTIFICATIONS
    ) == PermissionChecker.PERMISSION_GRANTED
}
