package com.irisvpn.android.androidSpecific

import android.content.Context
import com.irisvpn.android.domain.platform.ConnectCountRepository

class ConnectCountPreferences(context: Context) : ConnectCountRepository {
    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    override fun getSuccessfulConnectCount(): Int = prefs.getInt("SUCCESS_COUNT", 0)

    override fun incrementConnectCount() =
        prefs.edit().putInt("SUCCESS_COUNT", getSuccessfulConnectCount().inc()).apply()
}