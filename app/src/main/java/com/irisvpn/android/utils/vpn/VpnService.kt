package com.irisvpn.android.utils.vpn

import androidx.annotation.WorkerThread

interface VpnService {
    fun init()
    fun start(server: IrisServer)
    fun stop()
    fun getCurrentServer(): IrisServer
    fun getConnectionState(): IrisConnectionState
    fun getMode(): IrisConnectionMode
    fun onReceiveCurrentState(@WorkerThread onReceive: (ConnectionStats) -> Unit)
}

data class ConnectionStats(
    val ping: Ping,
    val speed: Speed,
    val traffic: Traffic,
    val time: ConnectionTime,
    val mode: IrisConnectionMode,
    val connectionState: IrisConnectionState
)

enum class IrisConnectionState {
    CONNECTED,
    CONNECTING,
    DISCONNECTED,
}

enum class IrisConnectionMode {
    PROXY,
    VPN
}

data class Ping(
    val ping: Long
)

data class ConnectionTime(
    val time: String
)

data class Speed(
    val upload: String,
    val download: String
)

data class Traffic(
    val upload: String,
    val download: String
)

data class IrisServer(
    val name: String,
    val serverString: String
)