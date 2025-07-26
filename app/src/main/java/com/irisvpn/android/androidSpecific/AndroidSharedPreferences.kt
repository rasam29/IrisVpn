package com.irisvpn.android.androidSpecific

import android.content.Context
import com.irisvpn.android.domain.platform.Preference
import com.irisvpn.android.domain.server.AvailableServer
import kotlinx.serialization.json.Json

class AndroidSharedPreferences(context: Context) : Preference {
    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    override fun toggleAutoConnect(isEnabled: Boolean) {
        prefs.edit().putBoolean("AUTO_CONNECT", isEnabled).apply()
    }

    override fun getAutoConnect(): Boolean {
        return prefs.getBoolean("AUTO_CONNECT", false)
    }

    override fun saveExcludedApp(packageNames: String) {
        prefs.edit().putString("EXCLUDED", packageNames).apply()
    }

    override fun getExcludedApps(): String {
        return prefs.getString("EXCLUDED", "") ?: ""
    }

    override fun saveCurrentSelectedServer(availableServer: AvailableServer) {
        prefs.edit().putString("current", Json.encodeToString(availableServer)).apply()
    }

    override fun getCurrentSelectedServer(): AvailableServer? {
        val str = prefs.getString("EXCLUDED", "")
        if (str.isNullOrBlank()) return null
        return Json.decodeFromString<AvailableServer>(str)
    }
}