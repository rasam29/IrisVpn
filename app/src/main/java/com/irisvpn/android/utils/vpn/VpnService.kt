package com.irisvpn.android.utils.vpn

interface VpnService {
    fun init()
    fun start(server:IrisService)
    fun stop()
    fun getCurrentServer():IrisService
    fun getConnectionState():IrisConnectionState
    fun getMode():IrisConnectionMode
    fun getPing(): Ping
    fun getConnectionTraffic(): Traffic
    fun getConnectionSpeed()
    fun getConnectionTime()
}

enum class IrisConnectionState{
    CONNECTED,
    CONNECTING,
    DISCONNECTED,
}

enum class IrisConnectionMode{
    PROXY,
    VPN
}

data class Ping(
    val ping: Long
)
data class Speed(

)

data class Traffic(
    val upload: String,
    val download: String
)


data class IrisServer(
    val serverString: String
)