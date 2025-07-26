package com.irisvpn.android.androidSpecific

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.irisvpn.android.domain.platform.Preference
import org.koin.java.KoinJavaComponent.inject

//class BootReceiver : BroadcastReceiver() {
//    private val preference: Preference by inject()
//    override fun onReceive(context: Context, intent: Intent?) {
//        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
//            val settingsManager = AndroidSharedPreferences(context)
//            if (settingsManager.getAutoConnect() &&  preference.getCurrentSelectedServer() != null) {
//
//            }
//        }
//    }
//}