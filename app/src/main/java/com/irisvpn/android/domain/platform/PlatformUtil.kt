package com.irisvpn.android.domain.platform

import androidx.compose.runtime.Composable
import com.irisvpn.android.domain.server.AvailableServer
import com.irisvpn.android.widgets.InstalledApp

interface PlatformState {
    fun launchTelegram(telegramChannelName: String)
    fun effectOnConnect()
    fun closeApplication()
}

interface Preference {
    fun toggleAutoConnect(isEnabled: Boolean)
    fun getAutoConnect(): Boolean
    fun saveExcludedApp(packageNames: String)
    fun getExcludedApps(): String
    fun saveCurrentSelectedServer(availableServer: AvailableServer)
    fun getCurrentSelectedServer(): AvailableServer?
}

interface ConnectCountRepository {
    fun getSuccessfulConnectCount(): Int
    fun incrementConnectCount()
}

interface AppPackagesRepository {
    suspend fun getInstalledApp(): List<InstalledApp>
}

@Composable
fun rememberPlatformState(): PlatformState {
    TODO("add expect to start of the function for compose multiplatform")
}