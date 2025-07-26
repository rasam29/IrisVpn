package com.irisvpn.android.domain.vpn

import com.irisvpn.android.domain.session.IrisSession
import kotlinx.coroutines.flow.Flow

interface VpnService {
    fun init()
    fun start(configName: String, session: IrisSession)
    fun stop()
    fun getConnectionState(): IrisConnectionState
    fun getMode(): IrisConnectionMode
    fun unRegisterToReceiver()
    val onReceiveCurrentState: Flow<ConnectionStats>
}

data class ConnectionStats(
    val ping: Ping,
    val speed: Speed,
    val traffic: Traffic,
    val time: ConnectionTime,
    val mode: IrisConnectionMode,
){
    companion object {
        fun DisConnected() = ConnectionStats(
            ping = Ping(0),
            speed = Speed("0 KB/s", "0 KB/s"),
            traffic = Traffic("0 B", "0 B"),
            time = ConnectionTime("00:00:00"),
            mode = IrisConnectionMode.VPN,
        )
    }
}

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