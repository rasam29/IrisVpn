package com.irisvpn.android.utils.vpn

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class IrisDomain(
    private val irisServer: IrisService,
    private val adService: AdService,
    private val vpnService: VpnService,
    private val serverRepository:ServerRepository
) {




    fun toggleConnection(coroutineScope: CoroutineScope) {

        when (vpnService.getConnectionState()) {
            IrisConnectionState.CONNECTED -> {
                TODO()
            }
            IrisConnectionState.CONNECTING -> {
                //do nothing
            }
            IrisConnectionState.DISCONNECTED -> {
                coroutineScope.launch{
                    irisServer.connect()
                }
            }
        }
    }
}