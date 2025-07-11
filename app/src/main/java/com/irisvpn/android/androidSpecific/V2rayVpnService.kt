package com.irisvpn.android.androidSpecific

import androidx.activity.ComponentActivity
import com.irisvpn.android.R
import com.irisvpn.android.domain.parsePackageName
import com.irisvpn.android.utils.Preference
import com.irisvpn.android.utils.vpn.ConnectionStats
import com.irisvpn.android.utils.vpn.IrisConnectionMode
import com.irisvpn.android.utils.vpn.IrisConnectionState
import com.irisvpn.android.utils.vpn.IrisServer
import com.irisvpn.android.utils.vpn.VpnService
import dev.dev7.lib.v2ray.V2rayController
import dev.dev7.lib.v2ray.utils.V2rayConfigs
import dev.dev7.lib.v2ray.utils.V2rayConstants
import dev.dev7.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.CONNECTED
import dev.dev7.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.CONNECTING
import dev.dev7.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.DISCONNECTED

class V2rayVpnService(val context: ComponentActivity, val preference: Preference) : VpnService {
    override fun init() {
        V2rayController.init(
            context,
            R.drawable.logo,
            context.getString(R.string.app_complete_name)
        )
    }

    override fun start(server: IrisServer) {
        V2rayController.startV2ray(
            context,
            "Test Server",
            "vless://d2051c83-df56-4002-a7ce-0cfb5e8b85cc@217.60.38.27:8001?type=tcp&security=none#HongKong-VLESS-vl-rasam",
            preference.getExcludedApps().parsePackageName() as ArrayList
        )
    }

    override fun stop() {
        V2rayController.stopV2ray(context)
    }

    override fun getCurrentServer(): IrisServer {
        return IrisServer(
            "Test Server",
            "vless://d2051c83-df56-4002-a7ce-0cfb5e8b85cc@217.60.38.27:8001?type=tcp&security=none#HongKong-VLESS-vl-rasam",
        )
    }

    override fun getConnectionState(): IrisConnectionState {
        V2rayController.getConnectionState().let {
            return when (it) {
                CONNECTED -> IrisConnectionState.CONNECTED
                CONNECTING -> IrisConnectionState.CONNECTING
                DISCONNECTED -> IrisConnectionState.DISCONNECTED
            }
        }
    }

    override fun getMode(): IrisConnectionMode = when (V2rayConfigs.serviceMode.name) {
        V2rayConstants.SERVICE_MODES.VPN_MODE.name -> IrisConnectionMode.VPN
        V2rayConstants.SERVICE_MODES.PROXY_MODE.name -> IrisConnectionMode.PROXY
        else -> IrisConnectionMode.VPN
    }

    override fun onReceiveCurrentState(onReceive: (ConnectionStats) -> Unit) {
        TODO("Not yet implemented")
    }
}