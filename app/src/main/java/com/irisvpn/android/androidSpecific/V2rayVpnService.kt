package com.irisvpn.android.androidSpecific

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.irisvpn.android.R
import com.irisvpn.android.domain.platform.Preference
import com.irisvpn.android.domain.session.IrisSession
import com.irisvpn.android.domain.vpn.ConnectionStats
import com.irisvpn.android.domain.vpn.ConnectionTime
import com.irisvpn.android.domain.vpn.IrisConnectionMode
import com.irisvpn.android.domain.vpn.IrisConnectionState
import com.irisvpn.android.domain.vpn.Ping
import com.irisvpn.android.domain.vpn.Speed
import com.irisvpn.android.domain.vpn.Traffic
import com.irisvpn.android.domain.vpn.VpnService
import com.irisvpn.android.screens.excludeApp.parsePackageName
import dev.dev7.lib.v2ray.V2rayController
import dev.dev7.lib.v2ray.utils.V2rayConfigs
import dev.dev7.lib.v2ray.utils.V2rayConstants
import dev.dev7.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.CONNECTED
import dev.dev7.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.CONNECTING
import dev.dev7.lib.v2ray.utils.V2rayConstants.CONNECTION_STATES.DISCONNECTED
import dev.dev7.lib.v2ray.utils.V2rayConstants.SERVICE_DOWNLOAD_SPEED_BROADCAST_EXTRA
import dev.dev7.lib.v2ray.utils.V2rayConstants.SERVICE_DOWNLOAD_TRAFFIC_BROADCAST_EXTRA
import dev.dev7.lib.v2ray.utils.V2rayConstants.SERVICE_DURATION_BROADCAST_EXTRA
import dev.dev7.lib.v2ray.utils.V2rayConstants.SERVICE_UPLOAD_SPEED_BROADCAST_EXTRA
import dev.dev7.lib.v2ray.utils.V2rayConstants.SERVICE_UPLOAD_TRAFFIC_BROADCAST_EXTRA
import dev.dev7.lib.v2ray.utils.V2rayConstants.V2RAY_SERVICE_STATICS_BROADCAST_INTENT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class V2rayVpnService(val context: Context, val preference: Preference) : VpnService {


    private val _onReceiveCurrentState = MutableStateFlow(ConnectionStats.DisConnected())

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.extras?.let { extras ->
                val serviceMode =
                    if (V2rayConfigs.serviceMode == V2rayConstants.SERVICE_MODES.VPN_MODE) IrisConnectionMode.VPN else IrisConnectionMode.PROXY
                _onReceiveCurrentState.value = ConnectionStats(
                    ping = Ping(0),
                    speed = Speed(
                        extras.getString(SERVICE_UPLOAD_SPEED_BROADCAST_EXTRA) ?: "",
                        extras.getString(SERVICE_DOWNLOAD_SPEED_BROADCAST_EXTRA) ?: ""
                    ),
                    traffic = Traffic(
                        extras.getString(SERVICE_UPLOAD_TRAFFIC_BROADCAST_EXTRA) ?: "",
                        extras.getString(SERVICE_DOWNLOAD_TRAFFIC_BROADCAST_EXTRA) ?: ""
                    ),
                    time = ConnectionTime(extras.getString(SERVICE_DURATION_BROADCAST_EXTRA) ?: ""),
                    mode = serviceMode
                )
            }
        }
    }

    init {
        ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(V2RAY_SERVICE_STATICS_BROADCAST_INTENT),
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    override fun init() {
        V2rayController.init(
            context, R.drawable.logo, context.getString(R.string.app_complete_name)
        )
    }

    override fun start(configName: String, session: IrisSession) {
        V2rayController.startV2ray(
            context,
            configName,
            session.config,
            ArrayList(preference.getExcludedApps().parsePackageName())
        )
    }

    override fun stop() {
        V2rayController.stopV2ray(context)
        context.unregisterReceiver(receiver)
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

    override fun unRegisterToReceiver() {
        context.unregisterReceiver(receiver)
    }

    override val onReceiveCurrentState: Flow<ConnectionStats>
        get() = _onReceiveCurrentState

}