package com.irisvpn.android.androidSpecific

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val settingsManager = AndroidSharedPreferences(context)
            if (settingsManager.getAutoConnect()) {
                //todo connect to vpn
            }
        }
    }
}